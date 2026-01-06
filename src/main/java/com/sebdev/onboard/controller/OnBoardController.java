package com.sebdev.onboard.controller;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;

import com.sebdev.onboard.model.Player;
import com.sebdev.onboard.model.Game;
import com.sebdev.onboard.model.Participation;
import com.sebdev.onboard.repository.PlayerRepository;
import com.sebdev.onboard.service.PlayerService;
import com.sebdev.onboard.service.GameService;
import com.sebdev.onboard.service.ParticipationService;
import com.sebdev.onboard.dto.UpdatePlayerDto;
import com.sebdev.onboard.dto.PlayerDto;
import com.sebdev.onboard.dto.GameDto;
import com.sebdev.onboard.dto.ParticipationDto;
import com.sebdev.onboard.mapper.DtoMapper;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "OnBoard", description = "Player, Game and Participation management endpoints")
@SecurityRequirement(name = "bearerAuth")
@RestController
public class OnBoardController {
    
    private PlayerRepository playerRepository;
    private final PlayerService playerService;
    private final GameService gameService;
    private final ParticipationService participationService;
    private final DtoMapper dtoMapper;
    
    @Autowired
    public OnBoardController(PlayerRepository playerRepository, PlayerService playerService, GameService gameService, ParticipationService participationService) {
        this.playerRepository = playerRepository;
        this.playerService = playerService;
        this.gameService = gameService;
        this.participationService = participationService;
        this.dtoMapper = new DtoMapper(playerService);
    }

    // Backwards-compatible constructor used by existing tests or callers that don't provide ParticipationService
    public OnBoardController(PlayerRepository playerRepository, PlayerService playerService, GameService gameService) {
        this(playerRepository, playerService, gameService, null);
    }
    
    @GetMapping("/onboard")
    @Operation(summary = "Simple onboarding greeting")
    public String onboard() {
        return "Welcome Onboard !";
    }

    @GetMapping("/players/me")
    @Operation(summary = "Get the authenticated player's details")
    public ResponseEntity<PlayerDto> authenticatedPlayer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Player currentUser = (Player) authentication.getPrincipal();
        return ResponseEntity.ok(dtoMapper.toPlayerDto(currentUser));
    }

    @GetMapping("/players")
    @Operation(summary = "List all players")
    public ResponseEntity<List<PlayerDto>> allUsers() {
        List <Player> users = playerService.allPlayers();
        List<PlayerDto> dtos = users.stream().map(dtoMapper::toPlayerDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping(value="/player")
    @Operation(summary = "Update a player's profile")
    public ResponseEntity<?> updatePlayer(@RequestBody UpdatePlayerDto updateDto) {
        if (updateDto == null || updateDto.getId() == null) {
            return ResponseEntity.badRequest().body("Player id is required for update");
        }

        try {
            // Map allowed fields from DTO to a Player instance. Authentication-related fields (email, password)
            // are intentionally not present in the DTO and will not be updated.
            Player player = new Player();
            player.setId(updateDto.getId());
            player.setFullName(updateDto.getFullName());
            player.setCity(updateDto.getCity());

            Optional<Player> updated = playerService.updatePlayer(player);
            if (updated.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(dtoMapper.toPlayerDto(updated.get()));
        } catch (IllegalArgumentException e) {
            // e.g. email already in use
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating player");
        }
    }

    @PostMapping(value = "/game")
    @Operation(summary = "Create a new game")
    public ResponseEntity<?> createGame(@Valid @RequestBody GameDto gameDto, BindingResult bindingResult, UriComponentsBuilder uriBuilder) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        if (gameDto == null) {
            return ResponseEntity.badRequest().body("Game payload is required");
        }
        if (gameDto.getAddress() == null || gameDto.getDate() == null || gameDto.getMaxPlayers() == null || gameDto.getMinPlayers() == null || gameDto.getGameType() == null) {
            return ResponseEntity.badRequest().body("Address, date, maxPlayers, minPlayers and gameType are required");
        }

        // Enforce host existence
        Optional<Player> hostOpt = playerService.findById(gameDto.getPlayerId());
        if (hostOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Host player not found");
        }

        try {
            Game game = dtoMapper.toGameEntity(gameDto);
            Game saved = gameService.saveGame(game);
            java.net.URI location = uriBuilder.path("/game/{id}").buildAndExpand(saved.getId()).toUri();
            return ResponseEntity.created(location).body(dtoMapper.toGameDto(saved));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating game");
        }
    }
    
    @PutMapping(value="/game")
    @Operation(summary = "Update a game")
    public ResponseEntity<?> updateGame(@Valid @RequestBody GameDto gameDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        if (gameDto == null || gameDto.getId() == null) {
            return ResponseEntity.badRequest().body("Game id is required for update");
        }

        // If playerId provided, ensure host exists
        if (gameDto.getPlayerId() != null) {
            Optional<Player> hostOpt = playerService.findById(gameDto.getPlayerId());
            if (hostOpt.isEmpty()) {
                return ResponseEntity.status(404).body("Host player not found");
            }
        }

        try {
            Game game = dtoMapper.toGameEntity(gameDto);
            Optional<Game> updated = gameService.updateGame(game);
            if (updated.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(dtoMapper.toGameDto(updated.get()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating game");
        }
    }

    @PostMapping(value = "/game/{id}/participation")
    @Operation(summary = "Add the authenticated player as a participant to a game")
    public ResponseEntity<?> addParticipation(@PathVariable("id") Long gameId, UriComponentsBuilder uriBuilder) {
        if (participationService == null) {
            // participation service not available (backward compatibility); fail explicitly
            return ResponseEntity.status(500).body("Participation service unavailable");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Player currentUser = (Player) authentication.getPrincipal();

        try {
            Optional<Participation> created = participationService.createParticipation(gameId, currentUser);
            if (created.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Participation p = created.get();
            java.net.URI location = uriBuilder.path("/game/{id}/participation/{pid}")
                    .buildAndExpand(gameId, p.getId()).toUri();
            return ResponseEntity.created(location).body(dtoMapper.toParticipationDto(p));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating participation");
        }
    }

    @PostMapping(value = "/participation")
    @Operation(summary = "Create a participation (admin/client) specifying playerId and gameId")
    public ResponseEntity<?> createParticipationFromDto(@Valid @RequestBody ParticipationDto participationDto, BindingResult bindingResult, UriComponentsBuilder uriBuilder) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        if (participationService == null) {
            return ResponseEntity.status(500).body("Participation service unavailable");
        }

        // Validate game existence
        Optional<Game> gameOpt = gameService.findById(participationDto.getGameId());
        if (gameOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Game not found");
        }

        // Validate player existence
        Optional<Player> playerOpt = playerService.findById(participationDto.getPlayerId());
        if (playerOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Player not found");
        }

        try {
            Participation created = participationService.createParticipation(participationDto.getGameId(), playerOpt.get()).orElseThrow(() -> new IllegalArgumentException("Unable to create participation"));
            java.net.URI location = uriBuilder.path("/game/{id}/participation/{pid}")
                    .buildAndExpand(participationDto.getGameId(), created.getId()).toUri();
            return ResponseEntity.created(location).body(dtoMapper.toParticipationDto(created));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating participation");
        }
    }

    // New endpoint: get a game by id including host and players
    @GetMapping(value = "/game/{id}")
    @Operation(summary = "Get a game by id with host and players")
    public ResponseEntity<?> getGameById(@PathVariable("id") Long gameId) {
        Optional<Game> gameOpt = gameService.findById(gameId);
        if (gameOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Game game = gameOpt.get();

        List<Player> players;
        if (participationService == null) {
            // Backward compatibility: if participation service not available, return empty player list
            players = List.of();
        } else {
            players = participationService.playersForGame(gameId).orElse(List.of());
        }

        Map<String, Object> body = new HashMap<>();
        body.put("game", dtoMapper.toGameDto(game));
        body.put("host", game.getPlayer() == null ? null : dtoMapper.toPlayerDto(game.getPlayer()));
        body.put("players", players.stream().map(dtoMapper::toPlayerDto).collect(Collectors.toList()));

        return ResponseEntity.ok(body);
    }

}
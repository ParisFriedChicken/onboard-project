package com.sebdev.onboard.controller;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import com.sebdev.onboard.model.Player;
import com.sebdev.onboard.model.Game;
import com.sebdev.onboard.model.Participation;
import com.sebdev.onboard.repository.PlayerRepository;
import com.sebdev.onboard.service.PlayerService;
import com.sebdev.onboard.service.GameService;
import com.sebdev.onboard.service.ParticipationService;

@RestController
public class OnBoardController {
    
    private PlayerRepository playerRepository;
    private final PlayerService playerService;
    private final GameService gameService;
    private final ParticipationService participationService;
    
    @Autowired
    public OnBoardController(PlayerRepository playerRepository, PlayerService playerService, GameService gameService, ParticipationService participationService) {
        this.playerRepository = playerRepository;
        this.playerService = playerService;
        this.gameService = gameService;
        this.participationService = participationService;
    }

    // Backwards-compatible constructor used by existing tests or callers that don't provide ParticipationService
    public OnBoardController(PlayerRepository playerRepository, PlayerService playerService, GameService gameService) {
        this(playerRepository, playerService, gameService, null);
    }
    
    @GetMapping("/onboard")
    public String onboard() {
        return "Welcome Onboard !";
    }

    @GetMapping("/players/me")
    public ResponseEntity<Player> authenticatedPlayer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Player currentUser = (Player) authentication.getPrincipal();
        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/players")
    public ResponseEntity<List<Player>> allUsers() {
        List <Player> users = playerService.allPlayers();
        return ResponseEntity.ok(users);
    }

    @PutMapping(value="/player")
    public ResponseEntity<?> updatePlayer(@RequestBody Player player) {
        if (player == null || player.getId() == null) {
            return ResponseEntity.badRequest().body("Player id is required for update");
        }

        try {
            Optional<Player> updated = playerService.updatePlayer(player);
            if (updated.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updated.get());
        } catch (IllegalArgumentException e) {
            // e.g. email already in use
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating player");
        }
    }

    @PostMapping(value = "/game")
    public ResponseEntity<?> createGame(@RequestBody Game game, UriComponentsBuilder uriBuilder) {
        if (game == null) {
            return ResponseEntity.badRequest().body("Game payload is required");
        }
        if (game.getAddress() == null || game.getDate() == null || game.getMaxPlayers() == null || game.getMinPlayers() == null || game.getGameType() == null) {
            return ResponseEntity.badRequest().body("Address, date, maxPlayers, minPlayers and gameType are required");
        }

        try {
            Game saved = gameService.saveGame(game);
            java.net.URI location = uriBuilder.path("/game/{id}").buildAndExpand(saved.getId()).toUri();
            return ResponseEntity.created(location).body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating game");
        }
    }
    
    @PutMapping(value="/game")
    public ResponseEntity<?> updateGame(@RequestBody Game game) {
        if (game == null || game.getId() == null) {
            return ResponseEntity.badRequest().body("Game id is required for update");
        }

        try {
            Optional<Game> updated = gameService.updateGame(game);
            if (updated.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updated.get());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating game");
        }
    }

    @PostMapping(value = "/game/{id}/participation")
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
            java.net.URI location = uriBuilder.path("/game/{id}/participation/{pid}")
                    .buildAndExpand(gameId, created.get().getId()).toUri();
            return ResponseEntity.created(location).body(created.get());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating participation");
        }
    }

    // New endpoint: get a game by id including host and players
    @GetMapping(value = "/game/{id}")
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
        body.put("game", game);
        body.put("host", game.getPlayer());
        body.put("players", players);

        return ResponseEntity.ok(body);
    }

}
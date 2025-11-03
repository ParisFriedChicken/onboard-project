package com.sebdev.onboard.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.sebdev.onboard.model.Player;
import com.sebdev.onboard.model.Game;
import com.sebdev.onboard.repository.PlayerRepository;
import com.sebdev.onboard.service.PlayerService;
import com.sebdev.onboard.service.GameService;

@RestController
public class OnBoardController {
	
	private PlayerRepository playerRepository;
    private final PlayerService playerService;
    private final GameService gameService;
	
	public OnBoardController(PlayerRepository playerRepository, PlayerService playerService, GameService gameService) {
		this.playerRepository = playerRepository;
		this.playerService = playerService;
		this.gameService = gameService;
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

	@DeleteMapping(value="/player")
	public ResponseEntity<Void> removePlayer(@RequestBody Player player) {
        if (player == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Player> target = Optional.empty();
        if (player.getId() != null) {
            target = playerService.findById(player.getId());
        } else if (player.getEmail() != null) {
            target = playerService.findByEmail(player.getEmail());
        } else {
            return ResponseEntity.badRequest().build();
        }

        if (target.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        playerService.deletePlayer(target.get());
        return ResponseEntity.noContent().build();
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
}
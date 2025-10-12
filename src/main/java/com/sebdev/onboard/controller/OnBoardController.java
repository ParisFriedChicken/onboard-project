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
import com.sebdev.onboard.repository.PlayerRepository;
import com.sebdev.onboard.service.PlayerService;

@RestController
public class OnBoardController {
	
	private PlayerRepository playerRepository;
    private final PlayerService playerService;
	
	public OnBoardController(PlayerRepository playerRepository, PlayerService playerService) {
		this.playerRepository = playerRepository;
		this.playerService = playerService;
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
	public void removePlayer(@RequestBody Player player) {
		this.playerRepository.delete(player);
	}

}

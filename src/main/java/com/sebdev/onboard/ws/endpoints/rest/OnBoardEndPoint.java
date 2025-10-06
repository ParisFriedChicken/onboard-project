package com.sebdev.onboard.ws.endpoints.rest;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.sebdev.onboard.service.UserService;
import com.sebdev.onboard.ws.entities.Player;
import com.sebdev.onboard.ws.repositories.PlayerRepository;

@RestController
public class OnBoardEndPoint {
	
	private PlayerRepository playerRepository;
	
	public OnBoardEndPoint(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}
	
	@GetMapping("/onboard")
	public String onboard() {
		return "Welcome Onboard !";
	}

	@GetMapping("/player")
	public Player getPlayer(@RequestParam Long id) {
		Optional<Player> player = this.playerRepository.findById(id);
		return player.get();
	}

	@GetMapping("/players")
	public Iterable<Player> getPlayers() {
		return this.playerRepository.findAll();
	}

	@PostMapping(value="/auth/signup")
	public void addPlayer(@RequestBody Player player) {
		this.playerRepository.save(player);
	}

	@PutMapping(value="/player")
	public void updatePlayer(@RequestBody Player player) {
		this.playerRepository.save(player);
	}

	@DeleteMapping(value="/player")
	public void removePlayer(@RequestBody Player player) {
		this.playerRepository.delete(player);
	}

}

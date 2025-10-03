package com.sebdev.onboard.ws.endpoints.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.sebdev.onboard.service.UserService;
import com.sebdev.onboard.ws.obj.Player;
import com.sebdev.onboard.ws.obj.PlayerRepository;

@RestController
public class OnBoardEndPoint {
	
	private RestTemplate template;
	private UserService userService;
	private PlayerRepository playerRepository;
	
	public OnBoardEndPoint(RestTemplate template, UserService userService, PlayerRepository playerRepository) {
		this.template = template;
		this.userService = userService;
		this.playerRepository = playerRepository;
	}
	
	@RequestMapping("/")
	public String hello() {
		return "Hello World !";
	}

	@RequestMapping("/onboard")
	public String onboard() {
		return "Welcome Onboard !";
	}

	@RequestMapping("/template")
	public String template() {
		String message = this.template.getForObject("http://localhost:8070", String.class);
		return message;
	}
	
	@RequestMapping("/player")
	public Player getPlayer(@RequestParam Long id) {
		Optional<Player> player = this.playerRepository.findById(id);
		return player.get();
	}

	@RequestMapping("/players")
	public Iterable<Player> getPlayers() {
		return this.playerRepository.findAll();
	}

	@RequestMapping(value="/player", method=RequestMethod.POST)
	public void addPlayer(@RequestBody Player player) {
		this.playerRepository.save(player);
	}

	@RequestMapping(value="/player", method=RequestMethod.PUT)
	public void updatePlayer(@RequestBody Player player) {
		this.playerRepository.save(player);
	}

	@RequestMapping(value="/player", method=RequestMethod.DELETE)
	public void removePlayer(@RequestBody Player player) {
		this.playerRepository.delete(player);
	}

}

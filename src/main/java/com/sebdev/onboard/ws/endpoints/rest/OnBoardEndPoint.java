package com.sebdev.onboard.ws.endpoints.rest;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.sebdev.onboard.service.UserService;
import com.sebdev.onboard.ws.obj.User;

@RestController
public class OnBoardEndPoint {
	
	private RestTemplate template;
	private UserService userService;
	
	public OnBoardEndPoint(RestTemplate template, UserService userService) {
		this.template = template;
		this.userService = userService;
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
	
	@RequestMapping("/user")
	public User getUser() {
		return this.userService.getUser();
	}

	@RequestMapping("/users")
	public List<User> getUsers() {
		return this.userService.getUsers();
	}

	@RequestMapping(value="/user", method=RequestMethod.POST)
	public void addUser(@RequestBody User user) {
		this.userService.addUser(user);
	}

	@RequestMapping(value="/user", method=RequestMethod.PUT)
	public void updateUser(@RequestBody User user) {
		this.userService.updateUser(user);
	}

	@RequestMapping(value="/user", method=RequestMethod.DELETE)
	public void removeUser(@RequestBody String id) {
		this.userService.removeUser(id);
	}

}

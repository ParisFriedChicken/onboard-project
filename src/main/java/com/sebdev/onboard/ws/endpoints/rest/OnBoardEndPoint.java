package com.sebdev.onboard.ws.endpoints.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OnBoardEndPoint {
	
	@RequestMapping("/")
	public String hello() {
		return "Hello World !";
	}

	@RequestMapping("/onboard")
	public String onboard() {
		return "Welcome Onboard !";
	}
}

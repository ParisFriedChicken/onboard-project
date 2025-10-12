package com.sebdev.onboard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sebdev.onboard.dto.LoginPlayerDto;
import com.sebdev.onboard.dto.RegisterPlayerDto;
import com.sebdev.onboard.model.Player;
import com.sebdev.onboard.responses.LoginResponse;
import com.sebdev.onboard.service.AuthenticationService;
import com.sebdev.onboard.service.JwtService;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Player> register(@RequestBody RegisterPlayerDto registerUserDto) {
        Player registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginPlayerDto loginPlayerDto) {
        Player authenticatedPlayer = authenticationService.authenticate(loginPlayerDto);

        String jwtToken = jwtService.generateToken(authenticatedPlayer);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}
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
import com.sebdev.onboard.dto.PlayerDto;
import com.sebdev.onboard.mapper.DtoMapper;
import com.sebdev.onboard.service.PlayerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints for user signup and login")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    
    private final AuthenticationService authenticationService;
    private final PlayerService playerService;
    private final DtoMapper dtoMapper;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, PlayerService playerService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.playerService = playerService;
        this.dtoMapper = new DtoMapper(playerService);
    }

    @PostMapping("/signup")
    @Operation(summary = "Register a new player")
    public ResponseEntity<PlayerDto> register(@RequestBody RegisterPlayerDto registerUserDto) {
        Player registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(dtoMapper.toPlayerDto(registeredUser));
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate a player and return a JWT token")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginPlayerDto loginPlayerDto) {
        Player authenticatedPlayer = authenticationService.authenticate(loginPlayerDto);

        String jwtToken = jwtService.generateToken(authenticatedPlayer);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        
        return ResponseEntity.ok(loginResponse);
    }
}
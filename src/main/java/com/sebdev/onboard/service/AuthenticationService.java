package com.sebdev.onboard.service;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sebdev.onboard.dto.LoginPlayerDto;
import com.sebdev.onboard.dto.RegisterPlayerDto;
import com.sebdev.onboard.model.Player;
import com.sebdev.onboard.repository.PlayerRepository;

@Service
public class AuthenticationService {
	
	private final PlayerRepository playerRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
    	PlayerRepository playerRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Player signup(RegisterPlayerDto input) {
        Player player = new Player(input.getFullName(), input.getEmail(), passwordEncoder.encode(input.getPassword()), null);

        return playerRepository.save(player);
    }

    public Player authenticate(LoginPlayerDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return playerRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}
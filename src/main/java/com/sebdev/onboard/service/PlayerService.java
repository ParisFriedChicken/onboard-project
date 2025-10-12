package com.sebdev.onboard.service;


import org.springframework.stereotype.Service;

import com.sebdev.onboard.model.Player;
import com.sebdev.onboard.repository.PlayerRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerService {
    private PlayerRepository playerRepository;
    
    public PlayerService() {}

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> allPlayers() {
        List<Player> players = new ArrayList<>();

        playerRepository.findAll().forEach(players::add);

        return players;
    }
}
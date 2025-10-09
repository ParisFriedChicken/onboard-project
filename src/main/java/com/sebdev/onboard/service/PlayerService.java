package com.sebdev.onboard.service;


import org.springframework.stereotype.Service;

import com.sebdev.onboard.ws.entities.Player;
import com.sebdev.onboard.ws.repositories.PlayerRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> allPlayers() {
        List<Player> players = new ArrayList<>();

        playerRepository.findAll().forEach(players::add);

        return players;
    }
}
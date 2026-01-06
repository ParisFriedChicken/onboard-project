package com.sebdev.onboard.service;


import org.springframework.stereotype.Service;

import com.sebdev.onboard.model.Player;
import com.sebdev.onboard.repository.PlayerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Instant;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private static final Logger logger = LoggerFactory.getLogger(PlayerService.class);
    

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> allPlayers() {
        List<Player> players = new ArrayList<>();

        playerRepository.findAll().forEach(players::add);

        return players;
    }

    // New helper methods to manage players

    public Optional<Player> findById(Long id) {
        return playerRepository.findById(id);
    }

    public Optional<Player> findByEmail(String email) {
        return playerRepository.findByEmail(email);
    }

    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    /**
     * Update an existing player. Fields that are non-null on the provided
     * player object will replace the existing ones. Email changes are
     * validated for uniqueness and will throw IllegalArgumentException if
     * the new email is already used by another player.
     *
     * @param player player payload (must contain id)
     * @return Optional containing the updated player, or empty if not found or id null
     */
    public Optional<Player> updatePlayer(Player player) {
        if (player == null || player.getId() == null) {
            return Optional.empty();
        }

        Optional<Player> existingOpt = playerRepository.findById(player.getId());
        if (existingOpt.isEmpty()) {
            return Optional.empty();
        }

        Player existing = existingOpt.get();

        // capture old values for logging
        String oldFullName = existing.getFullName();
        String oldCity = existing.getCity();

        // If the request tries to change the email, ensure uniqueness
        if (player.getEmail() != null && !player.getEmail().equals(existing.getEmail())) {
            Optional<Player> byEmail = playerRepository.findByEmail(player.getEmail());
            if (byEmail.isPresent() && !byEmail.get().getId().equals(existing.getId())) {
                throw new IllegalArgumentException("Email already in use by another player");
            }
            existing.setEmail(player.getEmail());
        }

        if (player.getFullName() != null) {
            existing.setFullName(player.getFullName());
        }

        if (player.getCity() != null) {
            existing.setCity(player.getCity());
        }

        if (player.getPassword() != null) {
            existing.setPassword(player.getPassword());
        }

        // capture new values for logging
        String newFullName = existing.getFullName();
        String newCity = existing.getCity();

        Player saved = playerRepository.save(existing);

        // Log the update with timestamp at INFO level
        logger.info("{} - Player updated: id={}, oldFullName='{}', oldCity='{}', newFullName='{}', newCity='{}'",
                Instant.now().toString(), saved.getId(), oldFullName, oldCity, newFullName, newCity);

        return Optional.of(saved);
    }

}
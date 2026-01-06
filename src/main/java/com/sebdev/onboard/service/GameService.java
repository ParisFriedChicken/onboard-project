package com.sebdev.onboard.service;

import org.springframework.stereotype.Service;

import com.sebdev.onboard.model.Game;
import com.sebdev.onboard.repository.GameRepository;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Instant;

@Service
public class GameService {
    private final GameRepository gameRepository;
    private static final Logger logger = LoggerFactory.getLogger(GameService.class);

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> allGames() {
        List<Game> games = new ArrayList<>();
        gameRepository.findAll().forEach(games::add);
        return games;
    }

    public Optional<Game> findById(Long id) {
        return gameRepository.findById(id);
    }

    public Game saveGame(Game game) {
        Game saved = gameRepository.save(game);
        // Log game creation: date, game_type, host's id, address
        String hostId = saved.getPlayer() == null ? null : String.valueOf(saved.getPlayer().getId());
        logger.info("{} - Game created: id={}, date={}, gameType='{}', hostId={}, address='{}'",
                Instant.now().toString(), saved.getId(), saved.getDate(), saved.getGameType(), hostId, saved.getAddress());
        return saved;
    }

    /**
     * Update an existing game. Non-null fields from the provided game will overwrite existing ones.
     * @param game payload with id
     * @return Optional containing updated game, or empty if not found or id null
     */
    @Transactional
    public Optional<Game> updateGame(Game game) {
        if (game == null || game.getId() == null) {
            return Optional.empty();
        }

        Optional<Game> existingOpt = gameRepository.findById(game.getId());
        if (existingOpt.isEmpty()) {
            return Optional.empty();
        }

        Game existing = existingOpt.get();

        if (game.getPlayer() != null) {
            existing.setPlayer(game.getPlayer());
        }
        if (game.getAddress() != null) {
            existing.setAddress(game.getAddress());
        }
        if (game.getDate() != null) {
            existing.setDate(game.getDate());
        }
        if (game.getStatus() != null) {
            existing.setStatus(game.getStatus());
        }
        if (game.getMaxPlayers() != null) {
            existing.setMaxPlayers(game.getMaxPlayers());
        }
        if (game.getMinPlayers() != null) {
            existing.setMinPlayers(game.getMinPlayers());
        }
        if (game.getGameType() != null) {
            existing.setGameType(game.getGameType());
        }
        if (game.getVersion() != null) {
            existing.setVersion(game.getVersion());
        }

        Game saved = gameRepository.save(existing);
        return Optional.of(saved);
    }

    public void deleteGame(Game game) {
        gameRepository.delete(game);
    }
}
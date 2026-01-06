package com.sebdev.onboard.service;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sebdev.onboard.model.Game;
import com.sebdev.onboard.model.Participation;
import com.sebdev.onboard.model.Player;
import com.sebdev.onboard.repository.ParticipationRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Instant;

@Service
public class ParticipationService {
    private final ParticipationRepository participationRepository;
    private final GameService gameService;
    private static final Logger logger = LoggerFactory.getLogger(ParticipationService.class);

    public ParticipationService(ParticipationRepository participationRepository, GameService gameService) {
        this.participationRepository = participationRepository;
        this.gameService = gameService;
    }

    /**
     * Try to create a participation for the given player on the given game id.
     * Returns Optional.empty() if the game does not exist.
     * Throws IllegalArgumentException when participation is not allowed (already participating or game full).
     */
    public Optional<Participation> createParticipation(Long gameId, Player player) {
        Optional<Game> gameOpt = gameService.findById(gameId);
        if (gameOpt.isEmpty()) {
            return Optional.empty();
        }

        Game game = gameOpt.get();

        // check if player already participates
        Optional<Participation> existing = participationRepository.findByGameAndPlayer(game, player);
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Player already participating in this game");
        }

        long currentCount = participationRepository.countByGame(game);
        if (game.getMaxPlayers() != null && currentCount >= game.getMaxPlayers()) {
            throw new IllegalArgumentException("Game is already full");
        }

        Participation participation = new Participation(game, player, "confirmed");
        Participation saved = participationRepository.save(participation);

        // Log participation creation: player's id, game id
        String playerId = player == null ? null : String.valueOf(player.getId());
        String gId = game == null ? null : String.valueOf(game.getId());
        logger.info("{} - Participation created: playerId={}, gameId={}", Instant.now().toString(), playerId, gId);

        return Optional.of(saved);
    }

    /**
     * Return list of players participating in a game. Optional.empty() if the game doesn't exist.
     */
    public Optional<List<Player>> playersForGame(Long gameId) {
        Optional<Game> gameOpt = gameService.findById(gameId);
        if (gameOpt.isEmpty()) {
            return Optional.empty();
        }
        Game game = gameOpt.get();
        List<Participation> participations = participationRepository.findByGame(game);
        List<Player> players = participations.stream()
                .map(Participation::getPlayer)
                .collect(Collectors.toList());
        return Optional.of(players);
    }
}
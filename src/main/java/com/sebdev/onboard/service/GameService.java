package com.sebdev.onboard.service;

import org.springframework.stereotype.Service;

import com.sebdev.onboard.dto.GameResponseDto;
import com.sebdev.onboard.dto.ParticipationPredictionRequestDto;
import com.sebdev.onboard.dto.ParticipationPredictionResponseDto;
import com.sebdev.onboard.model.Game;
import com.sebdev.onboard.repository.GameRepository;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Instant;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Service
public class GameService {
	
    private final GameRepository gameRepository;
    private static final Logger logger = LoggerFactory.getLogger(GameService.class);
    private final AiPredictionClientService aiPredictionClient;
    
    public GameService(GameRepository gameRepository, AiPredictionClientService aiPredictionClient) {
        this.gameRepository = gameRepository;
        this.aiPredictionClient = aiPredictionClient;
    }

    public List<Game> allGames() {
        List<Game> games = new ArrayList<>();
        gameRepository.findAll().forEach(games::add);
        return games;
    }

    public Optional<Game> findById(Long id) {
        return gameRepository.findById(id);
    }

    public GameResponseDto saveGame(Game game) {
        Game saved = gameRepository.save(game);
        GameResponseDto gameResponseDto = new GameResponseDto();
        
        // Log game creation: date, game_type, host's id, address
        String hostId = saved.getPlayer() == null ? null : String.valueOf(saved.getPlayer().getId());

        ParticipationPredictionRequestDto aiRequest =
                buildPredictionRequest(saved);

        Optional<ParticipationPredictionResponseDto> prediction =
                aiPredictionClient.predictParticipation(aiRequest);

        logger.info("{} - Game created: id={}, date={}, gameType='{}', hostId={}, address='{}'",
                Instant.now().toString(), saved.getId(), saved.getDate(), saved.getGameType(), hostId, saved.getAddress());
                
        // Populate response DTO with saved game data
        gameResponseDto.setId(saved.getId());
        gameResponseDto.setPlayerId(saved.getPlayer() == null ? null : saved.getPlayer().getId());
        gameResponseDto.setAddress(saved.getAddress());
        gameResponseDto.setDate(saved.getDate());
        gameResponseDto.setCreatedAt(saved.getCreatedAt());
        gameResponseDto.setUpdatedAt(saved.getUpdatedAt());
        gameResponseDto.setVersion(saved.getVersion());
        gameResponseDto.setStatus(saved.getStatus());
        gameResponseDto.setMaxPlayers(saved.getMaxPlayers());
        gameResponseDto.setMinPlayers(saved.getMinPlayers());
        gameResponseDto.setGameType(saved.getGameType());

        // Attach prediction result if present
        gameResponseDto.setParticipationPrediction(prediction.orElse(null));
        
        return gameResponseDto;
    }

    private ParticipationPredictionRequestDto buildPredictionRequest(Game game) {
		// Build a prediction request using available fields from Game.
		ParticipationPredictionRequestDto req = new ParticipationPredictionRequestDto();

		// Try to read the real registered players count from the DB view via repository.
		int registered = 0;
		if (game.getId() != null) {
			try {
				Integer dbRegistered = gameRepository.findRegisteredPlayersByGameId(game.getId());
				if (dbRegistered != null) {
					registered = dbRegistered.intValue();
				}
			} catch (Exception e) {
				logger.warn("Could not fetch registered players for game id {}: {}", game.getId(), e.getMessage());
				// keep default registered = 0
			}
		}
		req.setRegisteredPlayers(registered);

		// maxPlayers: from game (or 0)
		req.setMaxPlayers(game.getMaxPlayers() == null ? 0 : game.getMaxPlayers());

		// fillRatio: registered / maxPlayers (guard divide by zero)
		double fillRatio = 0d;
		if (req.getMaxPlayers() > 0) {
			fillRatio = (double) req.getRegisteredPlayers() / (double) req.getMaxPlayers();
		}
		req.setFillRatio(fillRatio);

		// hostTotalGames and hostNoShowRate: try to read from the full game/player features view
		int hostTotalGames = 0;
		double hostNoShowRate = 0d;
		Long hostId = game.getPlayer() == null ? null : game.getPlayer().getId();
		if (hostId != null) {
			try {
				Integer dbHostTotal = gameRepository.findHostTotalGamesByPlayerId(hostId);
				if (dbHostTotal != null) {
					hostTotalGames = dbHostTotal.intValue();
				}
				Double dbNoShow = gameRepository.findHostNoShowRateByPlayerId(hostId);
				if (dbNoShow != null) {
					hostNoShowRate = dbNoShow.doubleValue();
				}
			} catch (Exception e) {
				logger.warn("Could not fetch host features for host player id {}: {}", hostId, e.getMessage());
			}
		}
		req.setHostTotalGames(hostTotalGames);
		req.setHostNoShowRate(hostNoShowRate);

		// gameType: map string stored on game to encoded int used by the AI model
		int gameTypeEncoded = 0;
		String gt = game.getGameType();
		if (gt != null) {
			String lower = gt.trim().toLowerCase();
			switch (lower) {
				case "board_game":
					gameTypeEncoded = 1;
					break;
				case "outdoor_game":
					gameTypeEncoded = 2;
					break;
				default:
					gameTypeEncoded = 0;
			}
		}
		req.setGameType(gameTypeEncoded);

		// daysBeforeEvent: difference between now and game.date in days (rounded down)
		int daysBefore = 0;
		Date date = game.getDate();
		Date createdAt = game.getCreatedAt();
		if (date != null && createdAt != null) {
			ZonedDateTime now = ZonedDateTime.ofInstant(createdAt.toInstant(), ZoneId.systemDefault());
			ZonedDateTime event = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
			long seconds = Duration.between(now, event).getSeconds();
			daysBefore = (int) Math.floor(seconds / 86400.0);
			if (daysBefore < 0) daysBefore = 0;
		}
		req.setDaysBeforeEvent(daysBefore);

		return req;
	}

    /**
     * Return a prediction for the provided game using the AI prediction client.
     * Returns Optional.empty() if the game is null or the AI service is unavailable.
     */
    public Optional<ParticipationPredictionResponseDto> predictForGame(Game game) {
        if (game == null) {
            return Optional.empty();
        }
        ParticipationPredictionRequestDto request = buildPredictionRequest(game);
        return aiPredictionClient.predictParticipation(request);
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

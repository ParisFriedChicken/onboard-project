package com.sebdev.onboard.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sebdev.onboard.model.Game;
import com.sebdev.onboard.repository.GameRepository;
import com.sebdev.onboard.service.GameService;

@ExtendWith(MockitoExtension.class)
public class GameServiceUnitTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService gameService;

    @Test
    public void updateGame_shouldCopyNewFields() {
        // existing entity
        Game existing = new Game(null, "addr", new Date(), "open", 4, 2, "1");
        existing.setId(1L);

        // payload with new values to update
        Game payload = new Game();
        payload.setId(1L);
        payload.setMaxPlayers(8);
        payload.setMinPlayers(3);
        payload.setGameType("5");
        payload.setStatus("cancelled");

        when(gameRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(gameRepository.save(any(Game.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Game> result = gameService.updateGame(payload);

        assertTrue(result.isPresent());
        Game updated = result.get();
        assertEquals(8, updated.getMaxPlayers());
        assertEquals(3, updated.getMinPlayers());
        assertEquals("5", updated.getGameType());
        assertEquals("cancelled", updated.getStatus());

        verify(gameRepository).save(updated);
    }
}
package com.sebdev.onboard.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sebdev.onboard.model.Game;
import com.sebdev.onboard.model.Participation;
import com.sebdev.onboard.model.Player;
import com.sebdev.onboard.repository.ParticipationRepository;
import com.sebdev.onboard.service.GameService;
import com.sebdev.onboard.service.ParticipationService;

@ExtendWith(MockitoExtension.class)
public class ParticipationServiceUnitTest {

    @Mock
    private ParticipationRepository participationRepository;

    @Mock
    private GameService gameService;

    @InjectMocks
    private ParticipationService participationService;

    @Test
    public void createParticipation_shouldReturnEmpty_whenGameNotFound() {
        when(gameService.findById(1L)).thenReturn(Optional.empty());

        Optional<Participation> result = participationService.createParticipation(1L, new Player());

        assertTrue(result.isEmpty());
    }

    @Test
    public void createParticipation_shouldThrow_whenAlreadyParticipating() {
        Game game = new Game();
        game.setId(1L);
        Player player = new Player();
        player.setId(2L);

        Participation existing = new Participation(game, player);

        when(gameService.findById(1L)).thenReturn(Optional.of(game));
        when(participationRepository.findByGameAndPlayer(game, player)).thenReturn(Optional.of(existing));

        assertThrows(IllegalArgumentException.class, () -> participationService.createParticipation(1L, player));
    }

    @Test
    public void createParticipation_shouldThrow_whenGameIsFull() {
        Game game = new Game();
        game.setId(1L);
        game.setMaxPlayers(2);
        Player player = new Player();
        player.setId(2L);

        when(gameService.findById(1L)).thenReturn(Optional.of(game));
        when(participationRepository.findByGameAndPlayer(game, player)).thenReturn(Optional.empty());
        when(participationRepository.countByGame(game)).thenReturn(2L);

        assertThrows(IllegalArgumentException.class, () -> participationService.createParticipation(1L, player));
    }

    @Test
    public void createParticipation_shouldSaveAndReturnParticipation_whenOk() {
        Game game = new Game();
        game.setId(1L);
        game.setMaxPlayers(4);
        Player player = new Player();
        player.setId(2L);

        Participation toSave = new Participation(game, player, "confirmed");
        Participation saved = new Participation(game, player, "confirmed");
        saved.setId(10L);

        when(gameService.findById(1L)).thenReturn(Optional.of(game));
        when(participationRepository.findByGameAndPlayer(game, player)).thenReturn(Optional.empty());
        when(participationRepository.countByGame(game)).thenReturn(1L);
        when(participationRepository.save(any(Participation.class))).thenReturn(saved);

        Optional<Participation> result = participationService.createParticipation(1L, player);

        assertTrue(result.isPresent());
        assertEquals(10L, result.get().getId());
        verify(participationRepository).save(any(Participation.class));
    }
}

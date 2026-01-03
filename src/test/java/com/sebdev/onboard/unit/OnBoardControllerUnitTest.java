package com.sebdev.onboard.unit;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebdev.onboard.controller.OnBoardController;
import com.sebdev.onboard.model.Game;
import com.sebdev.onboard.repository.PlayerRepository;
import com.sebdev.onboard.service.GameService;
import com.sebdev.onboard.service.PlayerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

@ExtendWith(MockitoExtension.class)
public class OnBoardControllerUnitTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private PlayerService playerService;

    @Mock
    private GameService gameService;

    @BeforeEach
    public void setup() {
        OnBoardController controller = new OnBoardController(playerRepository, playerService, gameService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void createGame_shouldReturnCreated_whenPayloadValid() throws Exception {
        // prepare payload without status (should default to scheduled)
        var payload = new java.util.HashMap<String, Object>();
        payload.put("address", "somewhere");
        payload.put("date", "2026-01-01T12:00:00Z");
        payload.put("maxPlayers", 6);
        payload.put("minPlayers", 2);
        payload.put("gameType", "3");

        Game saved = new Game();
        saved.setId(10L);
        saved.setAddress("somewhere");
        saved.setDate(new Date());
        saved.setMaxPlayers(6);
        saved.setMinPlayers(2);
        saved.setGameType("3");
        saved.setStatus("scheduled");

        when(gameService.saveGame(org.mockito.ArgumentMatchers.any(Game.class))).thenReturn(saved);

        mockMvc.perform(post("/game")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(10));
    }

    @Test
    public void createGame_shouldReturnBadRequest_whenMissingRequired() throws Exception {
        var payload = new java.util.HashMap<String, Object>();
        payload.put("address", "somewhere");
        payload.put("date", "2026-01-01T12:00:00Z");
        // missing maxPlayers, minPlayers, gameType

        mockMvc.perform(post("/game")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isBadRequest());
    }
}
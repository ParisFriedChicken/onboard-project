package com.sebdev.onboard.mapper;

import java.util.Optional;

import com.sebdev.onboard.dto.GameDto;
import com.sebdev.onboard.dto.ParticipationDto;
import com.sebdev.onboard.dto.PlayerDto;
import com.sebdev.onboard.model.Game;
import com.sebdev.onboard.model.Participation;
import com.sebdev.onboard.model.Player;
import com.sebdev.onboard.service.PlayerService;

public class DtoMapper {

    private final PlayerService playerService;

    public DtoMapper(PlayerService playerService) {
        this.playerService = playerService;
    }

    public PlayerDto toPlayerDto(Player p) {
        if (p == null) return null;
        PlayerDto dto = new PlayerDto();
        dto.setId(p.getId());
        dto.setFullName(p.getFullName());
        dto.setCity(p.getCity());
        dto.setLastActiveAt(p.getLastActiveAt());
        return dto;
    }

    public GameDto toGameDto(Game g) {
        if (g == null) return null;
        GameDto dto = new GameDto();
        dto.setId(g.getId());
        dto.setPlayerId(g.getPlayer() == null ? null : g.getPlayer().getId());
        dto.setAddress(g.getAddress());
        dto.setDate(g.getDate());
        dto.setCreatedAt(g.getCreatedAt());
        dto.setUpdatedAt(g.getUpdatedAt());
        dto.setVersion(g.getVersion());
        dto.setStatus(g.getStatus());
        dto.setMaxPlayers(g.getMaxPlayers());
        dto.setMinPlayers(g.getMinPlayers());
        dto.setGameType(g.getGameType());
        return dto;
    }

    public ParticipationDto toParticipationDto(Participation p) {
        if (p == null) return null;
        ParticipationDto dto = new ParticipationDto();
        dto.setId(p.getId());
        dto.setGameId(p.getGame() == null ? null : p.getGame().getId());
        dto.setPlayerId(p.getPlayer() == null ? null : p.getPlayer().getId());
        dto.setAmount(p.getAmount());
        dto.setStatus(p.getStatus());
        dto.setCreatedAt(p.getCreatedAt());
        dto.setUpdatedAt(p.getUpdatedAt());
        return dto;
    }

    public Game toGameEntity(GameDto dto) {
        if (dto == null) return null;
        Game g = new Game();
        g.setId(dto.getId());
        if (dto.getPlayerId() != null) {
            Optional<Player> playerOpt = playerService.findById(dto.getPlayerId());
            playerOpt.ifPresent(g::setPlayer);
        }
        g.setAddress(dto.getAddress());
        g.setDate(dto.getDate());
        g.setVersion(dto.getVersion());
        g.setStatus(dto.getStatus());
        g.setMaxPlayers(dto.getMaxPlayers());
        g.setMinPlayers(dto.getMinPlayers());
        g.setGameType(dto.getGameType());
        return g;
    }
}

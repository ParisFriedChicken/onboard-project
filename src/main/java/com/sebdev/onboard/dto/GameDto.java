package com.sebdev.onboard.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import jakarta.validation.constraints.NotNull;

public class GameDto {

    private Long id;

    @Schema(description = "Host player id")
    @NotNull(message = "playerId is required")
    private Long playerId;

    @Schema(description = "Game address or venue", example = "123 Main St")
    private String address;

    @Schema(description = "Scheduled date/time for the game (ISO 8601)", example = "2026-01-01T14:00:00Z")
    private Date date;

    private Date createdAt;
    private Date updatedAt;
    private Long version;
    private String status;
    private Integer maxPlayers;
    private Integer minPlayers;
    private String gameType;

    public GameDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPlayerId() { return playerId; }
    public void setPlayerId(Long playerId) { this.playerId = playerId; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getMaxPlayers() { return maxPlayers; }
    public void setMaxPlayers(Integer maxPlayers) { this.maxPlayers = maxPlayers; }

    public Integer getMinPlayers() { return minPlayers; }
    public void setMinPlayers(Integer minPlayers) { this.minPlayers = minPlayers; }

    public String getGameType() { return gameType; }
    public void setGameType(String gameType) { this.gameType = gameType; }
}
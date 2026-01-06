package com.sebdev.onboard.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.Date;
import jakarta.validation.constraints.NotNull;

public class ParticipationDto {

    private Long id;

    @Schema(description = "Game id this participation belongs to")
    @NotNull(message = "gameId is required")
    private Long gameId;

    @Schema(description = "Player id participating in the game")
    @NotNull(message = "playerId is required")
    private Long playerId;

    @Schema(description = "Amount contributed by the player", example = "12.50")
    private BigDecimal amount;

    private String status;
    private Date createdAt;
    private Date updatedAt;

    public ParticipationDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getGameId() { return gameId; }
    public void setGameId(Long gameId) { this.gameId = gameId; }

    public Long getPlayerId() { return playerId; }
    public void setPlayerId(Long playerId) { this.playerId = playerId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
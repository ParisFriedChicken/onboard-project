package com.sebdev.onboard.dto;

import java.util.Date;

public class GameResponseDto {

    private Long id;
    private Long playerId;
    private String address;
    private Date date;
    private Date createdAt;
    private Date updatedAt;
    private Long version;
    private String status;
    private Integer maxPlayers;
    private Integer minPlayers;
    private String gameType;
    
    private ParticipationPredictionResponseDto participationPrediction;
    
    
    public GameResponseDto() {}
    
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

	public ParticipationPredictionResponseDto getParticipationPrediction() {
		return participationPrediction;
	}

	public void setParticipationPrediction(ParticipationPredictionResponseDto participationPrediction) {
		this.participationPrediction = participationPrediction;
	}
    
}

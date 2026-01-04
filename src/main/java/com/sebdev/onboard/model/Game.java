package com.sebdev.onboard.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Version;

@Entity
public class Game {

	@Id
	@SequenceGenerator(name = "game_seq_gen", sequenceName = "game_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "game_seq_gen")	
    @Column(nullable = false, name = "id")
    @Schema(description = "Unique game identifier", example = "1")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "host_player_id", foreignKey = @ForeignKey(name = "host_player_fkey"))
    @Schema(description = "Host player for this game")
	private Player player;
	
    @Column(nullable = false, name = "address")
    @Schema(description = "Game address or venue", example = "123 Main St")
    private String address; 

    @Column(nullable = false, name = "date")
    @Schema(description = "Scheduled date/time for the game (ISO 8601)", example = "2026-01-01T14:00:00Z")
    private Date date; 
    
    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    @Schema(description = "Timestamp when the game was created (read-only)", example = "2026-01-01T12:00:00Z")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Schema(description = "Timestamp when the game was last updated (read-only)", example = "2026-01-01T13:00:00Z")
    private Date updatedAt;

    @Version
    @Column(name = "version", nullable = false)
    @Schema(description = "Optimistic lock/version value", example = "0")
    private Long version;

    @Column(nullable = false, name = "status")
    @Schema(description = "Current game status", example = "scheduled")
    private String status;

    // New mandatory fields
    @Column(name = "max_players", nullable = false)
    @Schema(description = "Maximum number of players allowed", example = "8")
    private Integer maxPlayers;

    @Column(name = "min_players", nullable = false)
    @Schema(description = "Minimum number of players required", example = "2")
    private Integer minPlayers;

    @Column(name = "game_type", nullable = false)
    @Schema(description = "Type of the game", example = "board_game")
    private String gameType;
    
	public Game() {}
	
	public Game(Player player, String address, Date date) {
		this.player = player;
		this.address = address;
		this.date = date;
	}

	public Game(Player player, String address, Date date, String status) {
		this.player = player;
		this.address = address;
		this.date = date;
		this.status = status;
	}

	public Game(Player player, String address, Date date, String status, Integer maxPlayers, Integer minPlayers, String gameType) {
		this.player = player;
		this.address = address;
		this.date = date;
		this.status = status;
		this.maxPlayers = maxPlayers;
		this.minPlayers = minPlayers;
		this.gameType = gameType;
	}

	@PrePersist
	public void initVersion() {
		if (this.version == null) {
			this.version = 0L;
		}
		// Ensure status has default before persisting
		if (this.status == null) {
			this.status = "scheduled";
		}
		// mandatory numeric fields should be set by the caller; migrations handle DB defaults for existing data
	}

	@Override
	public String toString() {
		return String.format(
	        "Player[id=%d, player='%s', address='%s', date='%s', status='%s', maxPlayers='%s', minPlayers='%s', gameType='%s']",
	        id, player.toString(), address, date, status, maxPlayers, minPlayers, gameType);
	  }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getMaxPlayers() {
		return maxPlayers;
	}

	public void setMaxPlayers(Integer maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public Integer getMinPlayers() {
		return minPlayers;
	}

	public void setMinPlayers(Integer minPlayers) {
		this.minPlayers = minPlayers;
	}

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

}
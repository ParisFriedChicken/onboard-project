package com.sebdev.onboard.model;


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
	private Long id;

	@ManyToOne
	@JoinColumn(name = "host_player_id", foreignKey = @ForeignKey(name = "host_player_fkey"))
	private Player player;
	
    @Column(nullable = false, name = "address")
    private String address; 

    @Column(nullable = false, name = "date")
    private Date date; 
    
    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Column(nullable = false, name = "status")
    private String status;

    // New mandatory fields
    @Column(name = "max_players", nullable = false)
    private Integer maxPlayers;

    @Column(name = "min_players", nullable = false)
    private Integer minPlayers;

    @Column(name = "game_type", nullable = false)
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
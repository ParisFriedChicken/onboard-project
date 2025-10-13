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

@Entity
public class Participation {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
    @Column(nullable = false, name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "game_fkey"))
    private Game game;
    
    @ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "player_fkey"))
    private Player player;
    
    @Column
    private Boolean noFlake;
    
    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
    
	public Participation() {}
	
	public Participation(Game game, Player player, Boolean noFlake) {
		this.game = game;
		this.player = player;
		this.noFlake = noFlake;
	}

	@Override
	public String toString() {
		return String.format(
	        "Player[id=%d, game='%s', noFlake='%s']",
	        id, game.toString(), noFlake);
	  }
	
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Boolean getNoFlake() {
		return noFlake;
	}

	public void setNoFlake(Boolean noFlake) {
		this.noFlake = noFlake;
	}

}

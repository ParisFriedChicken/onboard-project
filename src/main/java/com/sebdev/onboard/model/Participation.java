package com.sebdev.onboard.model;

import java.math.BigDecimal;
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
import jakarta.persistence.SequenceGenerator;

@Entity
public class Participation {

	@Id
	@SequenceGenerator(name = "participation_seq_gen", sequenceName = "participation_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "participation_seq_gen")	
    @Column(nullable = false, name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "game_fkey"))
    private Game game;
    
    @ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "player_fkey"))
    private Player player;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, name = "status")
    private String status;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
    
	public Participation() {}
	
	public Participation(Game game, Player player) {
        this.game = game;
        this.player = player;
    }
	
	public Participation(Game game, Player player, String status) {
        this.game = game;
        this.player = player;
        this.status = status;
    }

	@Override
	public String toString() {
		return String.format(
	        "Player[id=%d, game='%s', status='%s']",
	        id, game.toString(), status);
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
     public Date getCreatedAt() {
         return createdAt;
     }

     public Date getUpdatedAt() {
         return updatedAt;
     }

}
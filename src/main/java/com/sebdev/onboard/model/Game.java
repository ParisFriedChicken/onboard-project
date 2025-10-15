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
public class Game {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
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
    
	public Game() {}
	
	public Game(Player player, String address, Date date) {
		this.player = player;
		this.address = address;
		this.date = date;
	}

	@Override
	public String toString() {
		return String.format(
	        "Player[id=%d, player='%s', address='%s', date='%s']",
	        id, player.toString(), address, date);
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

}

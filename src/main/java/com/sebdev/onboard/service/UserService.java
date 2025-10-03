package com.sebdev.onboard.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.sebdev.onboard.ws.obj.Player;

public class UserService {
	private List<Player> players = new ArrayList<Player>();
	
	public UserService() {
	}
	
	public Player getUser() {
		Random rn = new Random();
		int select = rn.nextInt(this.players.size());
		return this.players.get(select);
	}
	
	public List<Player> getUsers() {
		return this.players;
	}
	
	public void addUser(Player player) {
		this.players.add(player);
	}

	public void removeUser(String id) {
		Iterator<Player> it = this.players.iterator();
		while (it.hasNext()) {
			Player u = it.next();
			if (u.getId().equals(id)) {
				this.players.remove(u);
			}
		}
	}

	public void updateUser(Player player) {
		Iterator<Player> it = this.players.iterator();
		while (it.hasNext()) {
			Player u = it.next();
			if (u.getId().equals(player.getId())) {
				this.players.remove(u);
			}
		}
		this.players.add(player);
	}
}

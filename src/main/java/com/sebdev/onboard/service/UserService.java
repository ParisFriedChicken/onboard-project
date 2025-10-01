package com.sebdev.onboard.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.sebdev.onboard.ws.obj.User;

public class UserService {
	private List<User> users = new ArrayList<User>();
	
	public UserService() {
		
		this.users.add(new User("1", "Fabrice","Granjean","Dijon"));
		this.users.add(new User("2", "Daniel","Rutin","Pau"));
		this.users.add(new User("3", "Jaqueline","Daumier","Rennes"));
		this.users.add(new User("4", "Thais","Jonchère","Courbevoie"));
	
	}
	
	public User getUser() {
		Random rn = new Random();
		int select = rn.nextInt(this.users.size());
		return this.users.get(select);
	}
	
	public List<User> getUsers() {
		return this.users;
	}
	
	public void addUser(User user) {
		this.users.add(user);
	}

	public void removeUser(String id) {
		Iterator<User> it = this.users.iterator();
		while (it.hasNext()) {
			User u = it.next();
			if (u.getId().equals(id)) {
				this.users.remove(u);
			}
		}
	}

	public void updateUser(User user) {
		Iterator<User> it = this.users.iterator();
		while (it.hasNext()) {
			User u = it.next();
			if (u.getId().equals(user.getId())) {
				this.users.remove(u);
			}
		}
		this.users.add(user);
	}
}

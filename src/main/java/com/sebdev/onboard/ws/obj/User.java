package com.sebdev.onboard.ws.obj;

public class User {

	private String id;
	private String firstName;
	private String lastName;
	private String city;
	
	public User(String id, String firstName, String lastName, String city) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}

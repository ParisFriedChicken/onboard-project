package com.sebdev.onboard.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class RegisterPlayerDto {

	@Schema(description = "Email address of the player", example = "johndoe@gmail.com")
	private String email;

	@Schema(description = "Password of the player", example = "xxx")
	private String password;

	@Schema(description = "Full name of the player", example = "John Doe")
	private String fullName;

	@Schema(description = "City of the player", example = "Bristol")
	private String city;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}

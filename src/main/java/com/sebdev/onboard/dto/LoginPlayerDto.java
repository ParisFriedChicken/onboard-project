package com.sebdev.onboard.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class LoginPlayerDto {
    @Schema(description = "Player email address", example = "eg2@gmail.com")
    private String email;
    
    @Schema(description = "Player password (plain text for login request)", example = "password")
    private String password;

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
    

}
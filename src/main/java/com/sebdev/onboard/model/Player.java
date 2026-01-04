package com.sebdev.onboard.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Player implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Id
	@SequenceGenerator(name = "player_seq_gen", sequenceName = "player_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "player_seq_gen")	
    @Column(nullable = false, name = "id")
    @Schema(description = "Unique identifier for the player", example = "15456")	
	private Long id;

    @Column(nullable = false, name = "fullName")
	@Schema(description = "Full name of the player", example = "John Doe")
    private String fullName;
	
    @Column(unique = true, length = 100, nullable = false, name = "email")
	@Schema(description = "Email address of the player", example = "johndoe@gmail.com")
    private String email;
	
    @Column(nullable = false, name = "password")
	@Schema(description = "Password of the player", example = "xxx")
    private String password;
	
    @Column(nullable = true, name = "city")
	@Schema(description = "City of the player", example = "Bristol")
    private String city;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
	@Schema(description = "Registering date of the player", example = "2023-10-05T14:48:00.000Z")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
	@Schema(description = "Last update date of the player", example = "2023-10-05T14:48:00.000Z")
    private Date updatedAt;
    
	public Player() {}
	
	public Player(String fullName, String email, String password, String city) {
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.city = city;
	}

	@Override
	public String toString() {
		return String.format(
	        "Player[id=%d, fullName='%s', email='%s', city='%s']",
	        id, fullName, email, city);
	  }
	
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

	@Override
	public String getUsername() {
		return email;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


}
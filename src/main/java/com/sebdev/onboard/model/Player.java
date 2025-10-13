package com.sebdev.onboard.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Player implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
    @Column(nullable = false, name = "id")
	private Long id;

    @Column(nullable = false, name = "fullName")
    private String fullName;
	
    @Column(unique = true, length = 100, nullable = false, name = "email")
    private String email;
	
    @Column(nullable = false, name = "password")
    private String password;
	
    @Column(nullable = true, name = "city")
    private String city;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
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

package com.sebdev.onboard.ws.entities;

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
import jakarta.persistence.Table;

@Entity
public class Player implements UserDetails {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
    @Column(nullable = false)
	private Long id;

    @Column(nullable = false)
    private String firstName;
	
    @Column(nullable = false)
    private String lastName;
	
    @Column(unique = true, length = 100, nullable = false)
    private String email;
	
    @Column(nullable = false)
    private String password;
	
    @Column(nullable = false)
    private String city;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
    
	protected Player() {}
	
	public Player(String firstName, String lastName, String email, String password, String city) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.city = city;
	}

	@Override
	public String toString() {
		return String.format(
	        "Player[id=%d, firstName='%s', lastName='%s', email='%s', city='%s']",
	        id, firstName, lastName, email, city);
	  }
	
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

	@Override
	public String getUsername() {
		return email;
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

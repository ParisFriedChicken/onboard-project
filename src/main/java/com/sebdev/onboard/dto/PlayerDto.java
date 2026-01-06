package com.sebdev.onboard.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;

public class PlayerDto {

    @Schema(description = "Unique identifier for the player", example = "15456")
    private Long id;

    @Schema(description = "Full name of the player", example = "John Doe")
    private String fullName;

    @Schema(description = "City of the player", example = "Bristol")
    private String city;

    @Schema(description = "Last active timestamp", example = "2023-10-05T14:48:00.000Z")
    private Date lastActiveAt;

    public PlayerDto() {}

    public PlayerDto(Long id, String fullName, String city, Date lastActiveAt) {
        this.id = id;
        this.fullName = fullName;
        this.city = city;
        this.lastActiveAt = lastActiveAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getLastActiveAt() {
        return lastActiveAt;
    }

    public void setLastActiveAt(Date lastActiveAt) {
        this.lastActiveAt = lastActiveAt;
    }
}

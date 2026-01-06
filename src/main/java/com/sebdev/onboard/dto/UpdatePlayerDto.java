package com.sebdev.onboard.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class UpdatePlayerDto {

    @Schema(description = "Unique identifier for the player", example = "15456")
    private Long id;

    @Schema(description = "Full name of the player", example = "John Doe")
    private String fullName;

    @Schema(description = "City of the player", example = "Bristol")
    private String city;

    public UpdatePlayerDto() {}

    public UpdatePlayerDto(Long id, String fullName, String city) {
        this.id = id;
        this.fullName = fullName;
        this.city = city;
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
}

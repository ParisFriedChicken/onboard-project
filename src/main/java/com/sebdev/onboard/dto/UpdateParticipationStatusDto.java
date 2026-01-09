package com.sebdev.onboard.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

public class UpdateParticipationStatusDto {

    @NotBlank(message = "status is required")
    @Schema(description = "New participation status", example = "no_show")
    private String status;

    public UpdateParticipationStatusDto() {}

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

package com.sebdev.onboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParticipationPredictionResponseDto {

    @JsonProperty("participation_prediction")
    private double participationPrediction;
    
    @JsonProperty("risk_level")
    private String riskLevel;
    
	public double getParticipationPrediction() {
		return participationPrediction;
	}
	public void setParticipationPrediction(double participationPrediction) {
		this.participationPrediction = participationPrediction;
	}
	public String getRiskLevel() {
		return riskLevel;
	}
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
    
}

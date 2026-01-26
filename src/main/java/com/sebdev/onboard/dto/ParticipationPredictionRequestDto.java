package com.sebdev.onboard.dto;

public class ParticipationPredictionRequestDto {

	private double fillRatio;
	private double hostNoShowRate;
	private int registeredPlayers;
	private int maxPlayers;
	private int gameType;
	private int hostTotalGames;
	private int daysBeforeEvent;

	public double getFillRatio() {
		return fillRatio;
	}
	public void setFillRatio(double fillRatio) {
		this.fillRatio = fillRatio;
	}
	public double getHostNoShowRate() {
		return hostNoShowRate;
	}
	public void setHostNoShowRate(double hostNoShowRate) {
		this.hostNoShowRate = hostNoShowRate;
	}
	public int getRegisteredPlayers() {
		return registeredPlayers;
	}
	public void setRegisteredPlayers(int registeredPlayers) {
		this.registeredPlayers = registeredPlayers;
	}
	public int getMaxPlayers() {
		return maxPlayers;
	}
	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}
	public int getGameType() {
		return gameType;
	}
	public void setGameType(int gameType) {
		this.gameType = gameType;
	}
	public int getHostTotalGames() {
		return hostTotalGames;
	}
	public void setHostTotalGames(int hostTotalGames) {
		this.hostTotalGames = hostTotalGames;
	}
	public int getDaysBeforeEvent() {
		return daysBeforeEvent;
	}
	public void setDaysBeforeEvent(int daysBeforeEvent) {
		this.daysBeforeEvent = daysBeforeEvent;
	}

}

 package com.sebdev.onboard.service;

import java.time.Duration;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.sebdev.onboard.dto.ParticipationPredictionRequestDto;
import com.sebdev.onboard.dto.ParticipationPredictionResponseDto;

@Service
public class AiPredictionClientService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${ai.prediction.url}")
	private String aiPredictionUrl;
	
	private static final Logger logger = LoggerFactory.getLogger(AiPredictionClientService.class);

	public Optional<ParticipationPredictionResponseDto> predictParticipation(
			ParticipationPredictionRequestDto request) {

		try {
			ParticipationPredictionResponseDto response = restTemplate.postForObject(
					aiPredictionUrl,
					request,
					ParticipationPredictionResponseDto.class
					);

			logger.info(aiPredictionUrl);
			logger.info(response.getRiskLevel());

			return Optional.ofNullable(response);

		} catch (Exception e) {
			logger.warn("AI prediction unavailable", e);
			return Optional.empty();
		}
	}
}

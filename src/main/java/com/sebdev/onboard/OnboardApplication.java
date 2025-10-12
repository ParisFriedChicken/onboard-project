package com.sebdev.onboard;

import java.util.Optional;
import java.util.function.ToLongFunction;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.sebdev.onboard.model.Player;
import com.sebdev.onboard.repository.PlayerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@SpringBootApplication
public class OnboardApplication {

	private static final Logger log = LoggerFactory.getLogger(OnboardApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(OnboardApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}


	@Bean
	public CommandLineRunner demo(PlayerRepository repository) {
		return (args) -> {

			// fetch all players
			log.info("players found with findAll():");
			log.info("-------------------------------");
			repository.findAll().forEach(player -> {
				log.info(player.toString());
			});
			log.info("");

			// fetch an individual customer by ID
			Optional<Player> player = repository.findById(1L);
			log.info("Player found with findById(107L):");
			log.info("--------------------------------");
			log.info(player.toString());
			log.info("");

		};
	}
}

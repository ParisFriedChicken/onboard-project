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

import com.sebdev.onboard.ws.entities.Player;
import com.sebdev.onboard.ws.repositories.PlayerRepository;

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
			// save a few players
			repository.save(new Player("Fabrice Granjean", "fg@gmail.com", "fg", "Dijon"));
			repository.save(new Player("Daniel Rutin", "dr@gmail.com", "dr", "Pau"));
			repository.save(new Player("Jaqueline Daumier", "jd@gmail.com", "jd","Rennes"));
			repository.save(new Player("David Palmer", "dp@gmail.com", "dp", "Washington"));
			repository.save(new Player("Michelle Rutin", "mr@gmail.com", "mr", "Tokyo"));

			// fetch all players
			log.info("players found with findAll():");
			log.info("-------------------------------");
			repository.findAll().forEach(player -> {
				log.info(player.toString());
			});
			log.info("");

			// fetch an individual customer by ID
			Optional<Player> player = repository.findById(1L);
			log.info("Player found with findById(1L):");
			log.info("--------------------------------");
			log.info(player.toString());
			log.info("");

		};
	}
}

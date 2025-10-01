package com.sebdev.onboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.sebdev.onboard.service.UserService;

@SpringBootApplication
public class OnboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnboardApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@Bean
	public UserService getUserService() {
		return new UserService();
	}
}

package com.example.ms_mesas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MsMesasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsMesasApplication.class, args);
	}

	@Bean		// esto crea la instancia de resttemplate ypermite que esta sea inyyectada  por cualquier parte de mi codigo like mesa service
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}

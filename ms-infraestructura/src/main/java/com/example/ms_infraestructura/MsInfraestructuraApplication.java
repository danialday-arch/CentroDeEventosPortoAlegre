package com.example.ms_infraestructura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsInfraestructuraApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsInfraestructuraApplication.class, args);
	}

}

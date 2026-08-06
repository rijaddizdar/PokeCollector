package com.rijad.pokecollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PokecollectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PokecollectorApplication.class, args);
	}

}

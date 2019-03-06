package com.source.etracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EtrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EtrackerApplication.class, args);
	}

}


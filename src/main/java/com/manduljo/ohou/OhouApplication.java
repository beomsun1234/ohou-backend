package com.manduljo.ohou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class OhouApplication {

	public static void main(String[] args) {
		SpringApplication.run(OhouApplication.class, args);
	}

}

package com.heady.headyback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HeadyBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(HeadyBackApplication.class, args);
	}

}

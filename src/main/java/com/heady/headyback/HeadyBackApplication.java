package com.heady.headyback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HeadyBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(HeadyBackApplication.class, args);
	}

}

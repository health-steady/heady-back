package com.heady.headyback.common.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AsyncConfig {

	@Bean("argon2Executor")
	public Executor argon2Executor() {
		return Executors.newFixedThreadPool(4);
	}
}

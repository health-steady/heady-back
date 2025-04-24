package com.heady.headyback.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig implements WebFluxConfigurer {

	HttpClient httpClient = HttpClient.create()
			.followRedirect(true);

	@Bean
	public WebClient webClient() {
		ExchangeStrategies strategies = ExchangeStrategies.builder()
				.codecs((ClientCodecConfigurer configurer) -> {
					configurer.defaultCodecs()
							.maxInMemorySize(16 * 1024 * 1024);
				})
				.build();
		return WebClient.builder()
				.clientConnector(new ReactorClientHttpConnector(httpClient))
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.exchangeStrategies(strategies)
				.build();
	}
}

package com.heady.headyback.common.util;

import static java.nio.charset.StandardCharsets.*;

import java.net.URI;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.util.UriComponentsBuilder;

import com.heady.headyback.common.config.FoodApiProperties;
import com.heady.headyback.common.config.WebClientConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebClientUtil {

	private final WebClientConfig webClientConfig;
	private final FoodApiProperties foodApiProperties;

	public <T> T get(URI uri, Class<T> responseDto) {
		return webClientConfig.webClient().get()
				.uri(uri)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.onStatus(HttpStatusCode::isError, this::handleError)
				.bodyToMono(responseDto)
				.block();
	}

	public URI buildRequestUri(
			int pageNo,
			int numOfRows
	) {
		return UriComponentsBuilder
				.fromUri(URI.create(foodApiProperties.getBaseUrl()))
				.queryParam("serviceKey", foodApiProperties.getServiceKey())
				.queryParam("pageNo", pageNo)
				.queryParam("numOfRows", numOfRows)
				.queryParam("type", "json")
				.build()
				.encode()
				.toUri();
	}

	private Mono<? extends Throwable> handleError(ClientResponse clientResponse) {
		log.error("API 에러 응답: Status code {}", clientResponse.statusCode());
		return clientResponse.bodyToMono(String.class)
				.flatMap(body -> {
							log.error("API 에러 본문: {}", body);
							return Mono.error(
									new RuntimeException("API 요청 실패: " + clientResponse.statusCode()));
						}
				);
	}
}

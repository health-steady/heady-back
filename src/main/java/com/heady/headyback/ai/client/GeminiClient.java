package com.heady.headyback.ai.client;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heady.headyback.ai.dto.request.ContentRequest;
import com.heady.headyback.ai.dto.response.GeminiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class GeminiClient {

	private static final String BASE_URL =
			"https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";

	private final WebClient.Builder webClientBuilder;
	private final ObjectMapper objectMapper;

	@Value("${gemini.api.key}")
	private String apiKey;

	/**
	 * 동기 방식으로 Gemini API를 호출하고, 첫 번째 응답 파트를 텍스트로 반환합니다.
	 */
	public String generateContent(String prompt) {
		// 1) 요청 DTO 생성
		ContentRequest requestDto = new ContentRequest(
				List.of(new ContentRequest.Content(
						List.of(new ContentRequest.Part(prompt))
				))
		);

		// 2) DTO → JSON
		String body;
		try {
			body = objectMapper.writeValueAsString(requestDto);
		} catch (JsonProcessingException e) {
			log.error("Request serialization failed", e);
			throw new RuntimeException("Gemini 요청 직렬화 실패", e);
		}

		// 3) WebClient 생성 (매 호출마다 빌드해도 되고, 생성자에서 한 번만 만들어도 무방합니다)
		WebClient client = webClientBuilder
				.baseUrl(BASE_URL)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();

		GeminiResponse response;
		try {
			// 4) 동기 호출: block(Duration) 으로 타임아웃 설정 가능
			response = client.post()
					.uri(uriBuilder -> uriBuilder.queryParam("key", apiKey).build())
					.bodyValue(body)
					.retrieve()
					.onStatus(HttpStatusCode::is4xxClientError,
							resp -> resp.bodyToMono(String.class)
									.flatMap(msg -> Mono.error(new IllegalArgumentException("잘못된 요청: " + msg))))
					.onStatus(HttpStatusCode::is5xxServerError,
							resp -> resp.bodyToMono(String.class)
									.flatMap(msg -> Mono.error(new IllegalStateException("서버 오류: " + msg))))
					.bodyToMono(GeminiResponse.class)
					.timeout(Duration.ofSeconds(10))
					.block();  // <= 동기 처리 지점
		} catch (WebClientResponseException e) {
			log.error("Gemini API HTTP error {}: {}", e.getRawStatusCode(), e.getResponseBodyAsString(), e);
			throw new RuntimeException("Gemini API HTTP 오류", e);
		}

		// 5) 응답 검증 및 텍스트 추출
		if (response == null
				|| response.candidates() == null
				|| response.candidates().isEmpty()
				|| response.candidates().get(0).content().parts().isEmpty()) {
			throw new RuntimeException("Gemini 응답이 유효하지 않습니다.");
		}

		String result = response.candidates()
				.get(0)
				.content()
				.parts()
				.get(0)
				.text();
		log.debug("Gemini 동기 응답: {}", result);
		return result;
	}
}

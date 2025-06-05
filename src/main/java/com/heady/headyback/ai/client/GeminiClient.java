package com.heady.headyback.ai.client;

import java.time.Duration;
import java.util.List;
import java.util.Map;

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
import com.heady.headyback.ai.dto.AiAnalysisDto;
import com.heady.headyback.ai.dto.response.GeminiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class GeminiClient {

	@Value("${gemini.api.base-url}")
	private String BASE_URL;

	@Value("${gemini.api.key}")
	private String apiKey;

	private final WebClient.Builder webClientBuilder;
	private final ObjectMapper objectMapper;

	/**
	 * 동기 방식으로 JSON 스키마를 이용해 Gemini API를 호출하고,
	 * 첫 번째 응답 파트를 텍스트로 반환합니다.
	 */
	public AiAnalysisDto generateContent(String prompt) {
		var content = new ContentRequest.Content(
				List.of(new ContentRequest.Part(prompt))
		);

		Map<String, ContentRequest.PropertySchema> props = Map.of(
				"bloodSugarAnalysis", new ContentRequest.PropertySchema.TypeOnly("STRING"),
				"dietAnalysis",      new ContentRequest.PropertySchema.TypeOnly("STRING"),
				"recommendedActionPlan",
				new ContentRequest.PropertySchema.ArrayOfString(
						"ARRAY",
						new ContentRequest.PropertySchema.TypeOnly("STRING")
				)
		);
		ContentRequest.ResponseSchema responseSchema = new ContentRequest.ResponseSchema(
				"OBJECT",
				null,
				props,
				List.of("bloodSugarAnalysis", "dietAnalysis", "recommendedActionPlan")
		);
		var generationConfig = new ContentRequest.GenerationConfig(
				"application/json",
				responseSchema
		);

		var requestDto = new ContentRequest(
				List.of(content),
				generationConfig
		);

		String body;
		try {
			body = objectMapper.writeValueAsString(requestDto);
		} catch (JsonProcessingException e) {
			log.error("Request serialization failed", e);
			throw new RuntimeException("Gemini 요청 직렬화 실패", e);
		}

		WebClient client = webClientBuilder
				.baseUrl(BASE_URL)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();

		GeminiResponse response;
		try {
			response = client.post()
					.uri(uri -> uri.queryParam("key", apiKey).build())
					.bodyValue(body)
					.retrieve()
					.onStatus(HttpStatusCode::is4xxClientError,
							resp -> resp.bodyToMono(String.class)
									.flatMap(msg -> Mono.error(
											new IllegalArgumentException("잘못된 요청: " + msg))))
					.onStatus(HttpStatusCode::is5xxServerError,
							resp -> resp.bodyToMono(String.class)
									.flatMap(msg -> Mono.error(
											new IllegalStateException("서버 오류: " + msg))))
					.bodyToMono(GeminiResponse.class)
					.timeout(Duration.ofSeconds(10))
					.block();
		} catch (WebClientResponseException e) {
			log.error("Gemini API HTTP error {}: {}", e.getStatusCode(),
					e.getResponseBodyAsString(), e);
			throw new RuntimeException("Gemini API HTTP 오류", e);
		}

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

		try {
			return objectMapper.readValue(result, AiAnalysisDto.class);
		} catch (JsonProcessingException e) {
			log.error("분석 응답 파싱 실패: {}", result, e);
			throw new RuntimeException("Gemini 분석 응답 파싱 실패", e);
		}
	}
}

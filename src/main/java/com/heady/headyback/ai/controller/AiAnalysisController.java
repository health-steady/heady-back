package com.heady.headyback.ai.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heady.headyback.ai.dto.response.AiAnalysisResponse;
import com.heady.headyback.ai.service.AiAnalysisService;
import com.heady.headyback.auth.annotation.Auth;
import com.heady.headyback.auth.domain.Accessor;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai-analysis/v1")
public class AiAnalysisController {

	private final AiAnalysisService aiAnalysisService;

	@GetMapping
	public ResponseEntity<AiAnalysisResponse> createReport(@Auth Accessor accessor) {

		return ResponseEntity.ok(
				AiAnalysisResponse.from(
						aiAnalysisService.analyzeHealth(accessor)
				)
		);
	}
}

package com.heady.headyback.meal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heady.headyback.auth.annotation.Auth;
import com.heady.headyback.auth.domain.Accessor;
import com.heady.headyback.meal.dto.request.RecordRequest;
import com.heady.headyback.meal.dto.response.RecordResponse;
import com.heady.headyback.meal.service.MealService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meals/v1")
public class MealController {

	private final MealService mealService;

	@PostMapping
	public ResponseEntity<RecordResponse> record(
			@Auth Accessor accessor,
			@RequestBody @Valid RecordRequest request
	) {

		return ResponseEntity.ok().body(
				RecordResponse.of(
						mealService.record(accessor, request)
				)
		);
	}
}

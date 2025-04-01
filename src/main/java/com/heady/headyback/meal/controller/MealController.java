package com.heady.headyback.meal.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.heady.headyback.auth.annotation.Auth;
import com.heady.headyback.auth.domain.Accessor;
import com.heady.headyback.meal.dto.response.NutrientSummaryResponse;
import com.heady.headyback.meal.dto.request.MealRequest;
import com.heady.headyback.meal.dto.response.MealResponse;
import com.heady.headyback.meal.service.MealService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meals/v1")
public class MealController {

	private final MealService mealService;

	@PostMapping
	public ResponseEntity<MealResponse> createMeal(
			@Auth Accessor accessor,
			@RequestBody @Valid MealRequest request
	) {

		return ResponseEntity.ok().body(
				MealResponse.of(
						mealService.save(accessor, request)
				)
		);
	}

	@GetMapping("/nutrients/summary")
	public ResponseEntity<NutrientSummaryResponse> getNutrientSummary(
			@Auth Accessor accessor,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
	) {

		return ResponseEntity.ok().body(
				NutrientSummaryResponse.of(
						mealService.getNutrientSummary(accessor, date)
				)
		);
	}
}

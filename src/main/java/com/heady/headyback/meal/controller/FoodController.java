package com.heady.headyback.meal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.heady.headyback.meal.dto.response.FoodSearchResponse;
import com.heady.headyback.meal.service.FoodService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/foods/v1")
public class FoodController {

	private final FoodService foodService;

	@GetMapping("/search")
	public ResponseEntity<?> getFoods(
			@RequestParam("pageNo") int pageNo,
			@RequestParam("numOfRows") int numOfRows
	) {
		return ResponseEntity.ok().body(
				FoodSearchResponse.from(
						foodService.searchFoods(pageNo, numOfRows)
				)
		);
	}
}

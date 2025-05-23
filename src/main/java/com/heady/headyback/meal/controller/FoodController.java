package com.heady.headyback.meal.controller;

import org.springframework.data.domain.Page;
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
	public ResponseEntity<Page<FoodSearchResponse>> getFoods(
			@RequestParam("keyword") String keyword,
			@RequestParam(name = "pageNo", required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize
	) {
		return ResponseEntity.ok().body(
				foodService.searchFoods(keyword, pageNo, pageSize)
						.map(FoodSearchResponse::from)
		);
	}
}

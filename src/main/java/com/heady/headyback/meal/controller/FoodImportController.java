package com.heady.headyback.meal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heady.headyback.meal.service.FoodImportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/foods/v1/import")
public class FoodImportController {

	private final FoodImportService foodImportService;

	/**
	 * @return 저장된 총 Food 건수
	 */
	@PostMapping
	public ResponseEntity<String> importAll(
	) {
		int count = foodImportService.importAllFoods();
		return ResponseEntity.ok("총 " + count + "건의 Food 데이터를 저장했습니다.");
	}
}

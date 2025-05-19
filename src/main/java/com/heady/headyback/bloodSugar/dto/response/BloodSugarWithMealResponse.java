package com.heady.headyback.bloodSugar.dto.response;

import java.time.LocalDateTime;

import com.heady.headyback.bloodSugar.domain.enumerated.MeasureType;
import com.heady.headyback.bloodSugar.dto.BloodSugarWithMealDto;
import com.heady.headyback.meal.domain.enumerated.MealType;
import com.heady.headyback.meal.dto.response.MealResponse;

public record BloodSugarWithMealResponse(
		Long id,
		Integer level,
		LocalDateTime measuredAt,
		MeasureType measureType,
		String memo,
		MealType mealType,
		MealResponse meal
) {
	public static BloodSugarWithMealResponse from(BloodSugarWithMealDto bloodSugarWithMealDto) {
		return new BloodSugarWithMealResponse(
				bloodSugarWithMealDto.id(),
				bloodSugarWithMealDto.level(),
				bloodSugarWithMealDto.measuredAt(),
				bloodSugarWithMealDto.measureType(),
				bloodSugarWithMealDto.memo(),
				bloodSugarWithMealDto.mealType(),
				MealResponse.ofNullable(bloodSugarWithMealDto.mealDto())
		);
	}
}

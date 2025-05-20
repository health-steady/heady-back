package com.heady.headyback.bloodSugar.dto;

import java.time.LocalDateTime;

import com.heady.headyback.bloodSugar.domain.BloodSugar;
import com.heady.headyback.bloodSugar.domain.enumerated.MeasureType;
import com.heady.headyback.meal.domain.enumerated.MealType;
import com.heady.headyback.meal.dto.MealDto;
import com.heady.headyback.meal.dto.MealItemWithFoodDto;
import com.heady.headyback.meal.dto.MealWithMealItemWithFoodDto;

public record BloodSugarWithMealWithMealItemWithFoodDto(
		Long id,
		Integer level,
		LocalDateTime measuredAt,
		MeasureType measureType,
		String memo,
		MealType mealType,
		MealWithMealItemWithFoodDto mealItemWithFoodDto
) {
	public static BloodSugarWithMealWithMealItemWithFoodDto from(BloodSugar bloodSugar) {
		return new BloodSugarWithMealWithMealItemWithFoodDto(
				bloodSugar.getId(),
				bloodSugar.getLevel(),
				bloodSugar.getMeasuredAt(),
				bloodSugar.getMeasureType(),
				bloodSugar.getMemo(),
				bloodSugar.getMealType(),
				MealWithMealItemWithFoodDto.ofNullable(bloodSugar.getMeal())
		);
	}
}

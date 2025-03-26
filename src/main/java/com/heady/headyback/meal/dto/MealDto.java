package com.heady.headyback.meal.dto;

import java.time.LocalDateTime;

import com.heady.headyback.meal.domain.Meal;
import com.heady.headyback.meal.domain.enumerated.MealType;

public record MealDto(
		Long id,
		MealType mealType,
		LocalDateTime mealDateTime,
		String memo
) {
	public static MealDto of(Meal meal) {
		return new MealDto(
				meal.getId(),
				meal.getMealType(),
				meal.getMealDateTime(),
				meal.getMemo()
		);
	}
}

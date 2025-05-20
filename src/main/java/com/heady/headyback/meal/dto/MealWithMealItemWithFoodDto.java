package com.heady.headyback.meal.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.heady.headyback.meal.domain.Meal;
import com.heady.headyback.meal.domain.enumerated.MealType;

public record MealWithMealItemWithFoodDto(
		Long id,
		MealType mealType,
		LocalDateTime mealDateTime,
		String memo,
		List<MealItemWithFoodDto> mealItemWithFoodDtos
) {
	public static MealWithMealItemWithFoodDto of(Meal meal) {
		return new MealWithMealItemWithFoodDto(
				meal.getId(),
				meal.getMealType(),
				meal.getMealDateTime(),
				meal.getMemo(),
				meal.getMealItems().stream()
						.map(MealItemWithFoodDto::from)
						.collect(Collectors.toList())
		);
	}

	public static MealWithMealItemWithFoodDto ofNullable(Meal meal) {
		if (meal == null) return null;
		return of(meal);
	}
}

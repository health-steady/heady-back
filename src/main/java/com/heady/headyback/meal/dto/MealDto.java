package com.heady.headyback.meal.dto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import com.heady.headyback.meal.domain.Meal;
import com.heady.headyback.meal.domain.enumerated.MealType;

public record MealDto(
		Long id,
		MealType mealType,
		LocalDateTime mealDateTime,
		String memo,
		Set<FoodDto> foodDtos
) {
	public static MealDto of(Meal meal) {
		return new MealDto(
				meal.getId(),
				meal.getMealType(),
				meal.getMealDateTime(),
				meal.getMemo(),
				meal.getFoods().stream()
						.map(FoodDto::of)
						.collect(Collectors.toSet())
		);
	}

	public static MealDto ofNullable(Meal meal) {
		if (meal == null) return null;
		return of(meal);
	}
}

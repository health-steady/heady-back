package com.heady.headyback.meal.dto;

import com.heady.headyback.meal.domain.MealItem;

public record MealItemDto(
		Long id,
		String name
) {
	public static MealItemDto of(MealItem mealItem) {
		return new MealItemDto(mealItem.getId(), mealItem.getName());
	}
}

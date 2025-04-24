package com.heady.headyback.meal.dto;

import com.heady.headyback.meal.domain.MealItem;

public record MealItemDto(
		Long id,
		FoodDto foodDto
) {
	public static MealItemDto of(MealItem mealItem) {
		return new MealItemDto(mealItem.getId(), FoodDto.of(mealItem.getFood()));
	}
}

package com.heady.headyback.meal.dto;

import com.heady.headyback.meal.domain.MealItem;

public record MealItemWithFoodDto(
		Long id,
		String name,
		FoodDto foodDto
) {
	public static MealItemWithFoodDto from(MealItem mealItem) {
		return new MealItemWithFoodDto(
				mealItem.getId(),
				mealItem.getName(),
				FoodDto.fromNullable(mealItem.getFood())
		);
	}
}

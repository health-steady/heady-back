package com.heady.headyback.meal.dto;

import java.math.BigDecimal;

import com.heady.headyback.meal.domain.Food;

public record FoodDto(
		Long id,
		String name,
		BigDecimal calories
) {
	public static FoodDto of(Food food) {
		return new FoodDto(food.getId(), food.getName(), food.getCalories());
	}
}

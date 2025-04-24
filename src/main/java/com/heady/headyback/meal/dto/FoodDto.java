package com.heady.headyback.meal.dto;

import java.math.BigDecimal;

import com.heady.headyback.meal.domain.Food;

public record FoodDto(
		String code,
		String name,
		BigDecimal calories
) {
	public static FoodDto of(Food food) {
		return new FoodDto(food.getCode(), food.getName(), food.getCalories());
	}
}

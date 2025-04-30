package com.heady.headyback.meal.dto;

import java.math.BigDecimal;

import com.heady.headyback.meal.domain.Food;

public record FoodDto(
		String code,
		String name,
		String manufacturerName,
		BigDecimal calories,
		BigDecimal carbohydrates,
		BigDecimal sugar,
		BigDecimal fiber,
		BigDecimal protein,
		BigDecimal fat,
		BigDecimal satFat,
		BigDecimal transFat,
		BigDecimal sodium
) {
	public static FoodDto from(Food food) {
		return new FoodDto(
				food.getCode(),
				food.getName(),
				food.getManufacturerName(),
				food.getCalories(),
				food.getCarbohydrates(),
				food.getSugar(),
				food.getFiber(),
				food.getProtein(),
				food.getFat(),
				food.getSatFat(),
				food.getTransFat(),
				food.getSodium()
		);
	}
}

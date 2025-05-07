package com.heady.headyback.meal.domain;

public record FoodEntry(
		Food food,
		String name
) {
	public static FoodEntry of(Food food, String name) {
		return new FoodEntry(food, name);

	}
}

package com.heady.headyback.meal.dto.response;

import com.heady.headyback.meal.dto.MealItemDto;

public record MealItemResponse(
		Long id,
		String name
) {
	public static MealItemResponse of(MealItemDto mealItemDto) {
		return new MealItemResponse(mealItemDto.id(), mealItemDto.name());
	}
}

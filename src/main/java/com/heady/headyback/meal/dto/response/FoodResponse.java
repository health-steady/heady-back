package com.heady.headyback.meal.dto.response;

import com.heady.headyback.meal.dto.FoodDto;

public record FoodResponse(
		Long id,
		String name,
		Integer calories
) {
	public static FoodResponse of(FoodDto foodDto) {
		return new FoodResponse(foodDto.id(), foodDto.name(), foodDto.calories());
	}
}

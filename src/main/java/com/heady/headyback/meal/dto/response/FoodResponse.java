package com.heady.headyback.meal.dto.response;

import java.math.BigDecimal;

import com.heady.headyback.meal.dto.FoodDto;

public record FoodResponse(
		Long id,
		String name,
		BigDecimal calories
) {
	public static FoodResponse of(FoodDto foodDto) {
		return new FoodResponse(foodDto.id(), foodDto.name(), foodDto.calories());
	}
}

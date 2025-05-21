package com.heady.headyback.meal.dto.response;

import java.math.BigDecimal;

import com.heady.headyback.meal.dto.FoodDto;

public record FoodResponse(
		String code,
		String name,
		BigDecimal calories
) {
	public static FoodResponse from(FoodDto foodDto) {
		return new FoodResponse(foodDto.code(), foodDto.name(), foodDto.calories());
	}
}

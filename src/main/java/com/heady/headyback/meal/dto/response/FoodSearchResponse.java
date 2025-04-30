package com.heady.headyback.meal.dto.response;

import com.heady.headyback.meal.dto.FoodDto;

public record FoodSearchResponse(
		String code,
		String name,
		String manufacturerName
) {
	public static FoodSearchResponse from(FoodDto foodDto) {
		return new FoodSearchResponse(
				foodDto.code(),
				foodDto.name(),
				foodDto.manufacturerName()
		);
	}
}

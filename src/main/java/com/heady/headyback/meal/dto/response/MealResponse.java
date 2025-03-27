package com.heady.headyback.meal.dto.response;

import java.time.LocalDateTime;

import com.heady.headyback.meal.domain.enumerated.MealType;
import com.heady.headyback.meal.dto.MealDto;

public record MealResponse(
		Long id,
		MealType mealType,
		LocalDateTime mealDateTime,
		String memo
) {

	public static MealResponse of(MealDto mealDto) {
		return new MealResponse(
				mealDto.id(),
				mealDto.mealType(),
				mealDto.mealDateTime(),
				mealDto.memo()
		);
	}
}

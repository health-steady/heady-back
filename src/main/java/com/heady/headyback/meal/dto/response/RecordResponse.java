package com.heady.headyback.meal.dto.response;

import java.time.LocalDateTime;

import com.heady.headyback.meal.domain.enumerated.MealType;
import com.heady.headyback.meal.dto.MealDto;

public record RecordResponse(
		Long id,
		MealType mealType,
		LocalDateTime mealDateTime,
		String memo
) {

	public static RecordResponse of(MealDto mealDto) {
		return new RecordResponse(
				mealDto.id(),
				mealDto.mealType(),
				mealDto.mealDateTime(),
				mealDto.memo()
		);
	}
}

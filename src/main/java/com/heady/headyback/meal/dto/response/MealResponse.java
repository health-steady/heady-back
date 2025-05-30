package com.heady.headyback.meal.dto.response;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.heady.headyback.meal.domain.enumerated.MealType;
import com.heady.headyback.meal.dto.MealDto;

public record MealResponse(
		Long id,
		MealType mealType,
		LocalDateTime mealDateTime,
		String memo,
		List<MealItemResponse> foods
) {

	public static MealResponse of(MealDto mealDto) {
		return new MealResponse(
				mealDto.id(),
				mealDto.mealType(),
				mealDto.mealDateTime(),
				mealDto.memo(),
				mealDto.mealItemDtos().stream()
						.map(MealItemResponse::of)
						.sorted(Comparator.comparing(MealItemResponse::id))
						.collect(Collectors.toList())
		);
	}

	public static MealResponse ofNullable(MealDto mealDto) {
		if (mealDto == null) return null;
		return of(mealDto);
	}
}

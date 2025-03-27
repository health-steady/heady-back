package com.heady.headyback.bloodSugar.dto;

import java.time.LocalDateTime;

import com.heady.headyback.bloodSugar.domain.BloodSugar;
import com.heady.headyback.bloodSugar.domain.enumerated.MeasureType;
import com.heady.headyback.meal.dto.MealDto;

public record BloodSugarDto(
		Long id,
		Integer level,
		LocalDateTime measuredAt,
		MeasureType measureType,
		String memo,
		MealDto mealDto
) {
	public static BloodSugarDto of(BloodSugar bloodSugar) {
		return new BloodSugarDto(
				bloodSugar.getId(),
				bloodSugar.getLevel(),
				bloodSugar.getMeasuredAt(),
				bloodSugar.getMeasureType(),
				bloodSugar.getMemo(),
				MealDto.of(bloodSugar.getMeal())
		);
	}
}

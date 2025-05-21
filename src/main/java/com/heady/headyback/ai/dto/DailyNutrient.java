package com.heady.headyback.ai.dto;

import java.time.LocalDate;

import com.heady.headyback.meal.domain.Nutrient;

public record DailyNutrient(
		LocalDate date,
		Nutrient nutrient
) {
}

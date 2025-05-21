package com.heady.headyback.meal.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.heady.headyback.meal.dto.NutrientSummaryDto;

public record NutrientSummaryResponse(
		LocalDate date,
		BigDecimal carbohydrate,
		BigDecimal protein,
		BigDecimal fat
) {
	public static NutrientSummaryResponse of(NutrientSummaryDto nutrientSummaryDto) {
		return new NutrientSummaryResponse(
				nutrientSummaryDto.date(),
				nutrientSummaryDto.carbohydrate(),
				nutrientSummaryDto.protein(),
				nutrientSummaryDto.fat()
		);
	}
}

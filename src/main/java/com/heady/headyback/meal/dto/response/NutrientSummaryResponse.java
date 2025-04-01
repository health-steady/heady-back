package com.heady.headyback.meal.dto.response;

import java.math.BigDecimal;

import com.heady.headyback.meal.dto.NutrientSummaryDto;

public record NutrientSummaryResponse(
		BigDecimal carbohydrate,
		BigDecimal protein,
		BigDecimal fat
) {
	public static NutrientSummaryResponse of(NutrientSummaryDto nutrientSummaryDto) {
		return new NutrientSummaryResponse(
				nutrientSummaryDto.carbohydrate(),
				nutrientSummaryDto.protein(),
				nutrientSummaryDto.fat()
		);
	}
}

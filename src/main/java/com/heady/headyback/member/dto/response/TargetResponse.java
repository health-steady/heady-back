package com.heady.headyback.member.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.heady.headyback.member.dto.TargetDto;

public record TargetResponse(
		Long id,
		Integer fastingBloodSugar,
		Integer postprandialBloodSugar,
		BigDecimal carbohydrate,
		BigDecimal protein,
		BigDecimal fat,
		Integer calories,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
) {
	public static TargetResponse of(TargetDto targetDto) {
		return new TargetResponse(
				targetDto.id(),
				targetDto.fastingBloodSugar(),
				targetDto.postprandialBloodSugar(),
				targetDto.carbohydrate(),
				targetDto.protein(),
				targetDto.fat(),
				targetDto.calories(),
				targetDto.createdAt(),
				targetDto.updatedAt()
		);
	}
}

package com.heady.headyback.member.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.heady.headyback.member.domain.Target;

public record TargetDto(
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
	public static TargetDto of(Target target) {
		return new TargetDto(
				target.getId(),
				target.getFastingBloodSugar(),
				target.getPostprandialBloodSugar(),
				target.getCarbohydrate(),
				target.getProtein(),
				target.getFat(),
				target.getCalories(),
				target.getCreatedAt(),
				target.getUpdatedAt()
		);
	}
}

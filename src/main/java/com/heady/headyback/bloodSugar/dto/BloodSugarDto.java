package com.heady.headyback.bloodSugar.dto;

import java.time.LocalDateTime;

import com.heady.headyback.bloodSugar.domain.BloodSugar;
import com.heady.headyback.bloodSugar.domain.enumerated.MeasurementTimeType;

public record BloodSugarDto(
		Long id,
		Integer level,
		LocalDateTime measuredAt,
		MeasurementTimeType measurementTimeType,
		String memo
) {
	public static BloodSugarDto of(BloodSugar bloodSugar) {
		return new BloodSugarDto(
				bloodSugar.getId(),
				bloodSugar.getLevel(),
				bloodSugar.getMeasuredAt(),
				bloodSugar.getMeasurementTimeType(),
				bloodSugar.getMemo()
		);
	}
}

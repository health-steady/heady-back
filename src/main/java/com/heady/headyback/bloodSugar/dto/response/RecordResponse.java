package com.heady.headyback.bloodSugar.dto.response;

import java.time.LocalDateTime;

import com.heady.headyback.bloodSugar.domain.enumerated.MeasurementTimeType;
import com.heady.headyback.bloodSugar.dto.BloodSugarDto;

public record RecordResponse(
		Long id,
		Integer level,
		LocalDateTime measuredAt,
		MeasurementTimeType measurementTimeType,
		String memo
) {
	public static RecordResponse of(BloodSugarDto bloodSugarDto) {
		return new RecordResponse(
				bloodSugarDto.id(),
				bloodSugarDto.level(),
				bloodSugarDto.measuredAt(),
				bloodSugarDto.measurementTimeType(),
				bloodSugarDto.memo()
		);
	}
}

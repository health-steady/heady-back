package com.heady.headyback.bloodSugar.dto.response;

import java.time.LocalDateTime;

import com.heady.headyback.bloodSugar.domain.enumerated.MeasureType;
import com.heady.headyback.bloodSugar.dto.BloodSugarDto;

public record RecordResponse(
		Long id,
		Integer level,
		LocalDateTime measuredAt,
		MeasureType measureType,
		String memo
) {
	public static RecordResponse of(BloodSugarDto bloodSugarDto) {
		return new RecordResponse(
				bloodSugarDto.id(),
				bloodSugarDto.level(),
				bloodSugarDto.measuredAt(),
				bloodSugarDto.measureType(),
				bloodSugarDto.memo()
		);
	}
}

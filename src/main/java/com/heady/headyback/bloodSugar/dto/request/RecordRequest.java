package com.heady.headyback.bloodSugar.dto.request;

import java.time.LocalDateTime;

public record RecordRequest(
		LocalDateTime measuredAt,
		String measurementTimeType,
		String mealTimeType,
		Integer level,
		String memo
) {
}

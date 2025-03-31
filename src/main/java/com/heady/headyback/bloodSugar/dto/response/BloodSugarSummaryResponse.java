package com.heady.headyback.bloodSugar.dto.response;

import com.heady.headyback.bloodSugar.dto.BloodSugarSummaryDto;

public record BloodSugarSummaryResponse(
		Integer breakfast,
		Integer lunch,
		Integer dinner,
		Integer highestFasting,
		Integer highestPostprandial
) {
	public static BloodSugarSummaryResponse of(BloodSugarSummaryDto dto) {
		return new BloodSugarSummaryResponse(
				dto.breakfast(),
				dto.lunch(),
				dto.dinner(),
				dto.highestFasting(),
				dto.highestPostprandial()
		);
	}
}

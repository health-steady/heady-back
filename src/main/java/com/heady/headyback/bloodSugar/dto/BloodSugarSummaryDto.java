package com.heady.headyback.bloodSugar.dto;

public record BloodSugarSummaryDto(
	Integer breakfast,
	Integer lunch,
	Integer dinner,
	Integer highestFasting,
	Integer highestPostprandial
) {
}

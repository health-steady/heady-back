package com.heady.headyback.ai.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.heady.headyback.bloodSugar.dto.BloodSugarWithMealDto;
import com.heady.headyback.member.dto.MemberDto;

public record AiAnalysisDto(
		@JsonProperty("bloodSugarAnalysis") String bloodSugarAnalysis,
		@JsonProperty("dietAnalysis") String dietAnalysis,
		@JsonProperty("recommendedActionPlan") List<String> recommendedActionPlan,
		MemberDto memberDto,
		List<BloodSugarWithMealDto> bloodSugarWithMealDtos,
		List<DailyNutrient> dailyNutrients
) {
	@JsonCreator
	public AiAnalysisDto {
	}

	public static AiAnalysisDto from(
			AiAnalysisDto aiAnalysisDto,
			MemberDto memberDto,
			List<BloodSugarWithMealDto> bloodSugarWithMealDtos,
			List<DailyNutrient> dailyNutrients
	) {
		return new AiAnalysisDto(
				aiAnalysisDto.bloodSugarAnalysis(),
				aiAnalysisDto.dietAnalysis(),
				aiAnalysisDto.recommendedActionPlan(),
				memberDto,
				bloodSugarWithMealDtos,
				dailyNutrients
		);
	}
}

package com.heady.headyback.ai.util;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heady.headyback.bloodSugar.dto.BloodSugarWithMealWithMealItemWithFoodDto;
import com.heady.headyback.member.dto.MemberDto;

@Component
public class PromptBuilder {
	private final ObjectMapper objectMapper;

	public PromptBuilder(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	/**
	 * Gemini API 에 넘길 prompt 를 조립합니다.
	 *
	 * @param member 환자의 기본 정보 DTO
	 * @param records 혈당 + 식사·영양소 기록 DTO 리스트
	 * @return 완성된 prompt 문자열
	 */
	public String build(MemberDto member,
			List<BloodSugarWithMealWithMealItemWithFoodDto> records) {
		String memberJson;
		String recordsJson;
		try {
			memberJson = objectMapper.writeValueAsString(member);
			recordsJson = objectMapper.writeValueAsString(records);
		} catch (JsonProcessingException e) {
			throw new IllegalStateException("DTO → JSON 직렬화 실패", e);
		}

		StringBuilder sb = new StringBuilder();
		sb.append("당신은 세계적인 수준의 내분비내과 전문의입니다.\n")
				.append("아래에 주어진 환자의 기본 정보와 식사·영양소·혈당 기록을 종합하여:\n\n")
				.append("1. 혈당 분석 (bloodSugarAnalysis): 환자의 혈당 패턴과 주요 변동 원인을 의학적 근거에 기반해 간결하게 설명하세요.\n")
				.append("2. 식단 분석 (dietAnalysis): 제공된 식단 및 영양소 정보를 바탕으로 당 변동에 기여한 식습관 특징을 분석하세요.\n")
				.append("3. 권장 행동 계획 (recommendedActionPlan): 환자가 즉시 실행할 수 있는 맞춤형 행동 계획 5가지를 제안하세요.\n\n")
				.append("반드시 아래 JSON 포맷으로만 응답해주세요:\n")
				.append("{\n")
				.append("  \"bloodSugarAnalysis\": \"…\",\n")
				.append("  \"dietAnalysis\": \"…\",\n")
				.append("  \"recommendedActionPlan\": [\n")
				.append("    \"…\",\n")
				.append("    \"…\",\n")
				.append("    \"…\",\n")
				.append("    \"…\",\n")
				.append("    \"…\"\n")
				.append("  ]\n")
				.append("}\n\n")
				.append("### 환자 기본 정보 ###\n")
				.append(memberJson).append("\n\n")
				.append("### 혈당 및 식사·영양소 정보 ###\n")
				.append(recordsJson);

		return sb.toString();
	}
}

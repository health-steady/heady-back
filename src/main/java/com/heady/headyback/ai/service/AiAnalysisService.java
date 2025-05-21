package com.heady.headyback.ai.service;

import static com.heady.headyback.member.exception.MemberExceptionCode.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.heady.headyback.ai.client.GeminiClient;
import com.heady.headyback.ai.dto.AiAnalysisDto;
import com.heady.headyback.ai.dto.response.AiAnalysisResponse;
import com.heady.headyback.ai.util.PromptBuilder;
import com.heady.headyback.auth.domain.Accessor;
import com.heady.headyback.bloodSugar.domain.BloodSugar;
import com.heady.headyback.bloodSugar.dto.BloodSugarWithMealDto;
import com.heady.headyback.bloodSugar.dto.BloodSugarWithMealWithMealItemWithFoodDto;
import com.heady.headyback.bloodSugar.repository.BloodSugarRepository;
import com.heady.headyback.common.exception.CustomException;
import com.heady.headyback.member.dto.MemberDto;
import com.heady.headyback.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AiAnalysisService {

	private final MemberRepository memberRepository;
	private final BloodSugarRepository bloodSugarRepository;
	private final GeminiClient geminiClient;
	private final PromptBuilder promptBuilder;

	public AiAnalysisDto analyzeHealth(Accessor accessor) {
		MemberDto member = memberRepository.findByPublicId(accessor.getPublicId())
				.map(MemberDto::of)
				.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

		LocalDate now = LocalDate.now();
		List<BloodSugar> bloodSugarList = bloodSugarRepository.findByMemberAndPeriodWithMealAndMealItemAndFood(
				member.id(),
				now.minusDays(6).atStartOfDay(),
				now.atTime(LocalTime.MAX)
		);

		String prompt = promptBuilder.build(
				member,
				bloodSugarList.stream()
						.map(BloodSugarWithMealWithMealItemWithFoodDto::from)
						.toList()
		);

		return AiAnalysisDto.from(
				geminiClient.generateContent(prompt),
				member,
				bloodSugarList.stream()
						.map(BloodSugarWithMealDto::from)
						.toList()
		);
	}
}

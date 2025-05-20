package com.heady.headyback.ai.service;

import static com.heady.headyback.member.exception.MemberExceptionCode.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.heady.headyback.ai.client.GeminiClient;
import com.heady.headyback.auth.domain.Accessor;
import com.heady.headyback.bloodSugar.domain.BloodSugar;
import com.heady.headyback.bloodSugar.dto.BloodSugarWithMealWithMealItemWithFoodDto;
import com.heady.headyback.bloodSugar.repository.BloodSugarRepository;
import com.heady.headyback.common.exception.CustomException;
import com.heady.headyback.member.domain.Member;
import com.heady.headyback.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AiAnalysisService {

	private final MemberRepository memberRepository;
	private final BloodSugarRepository bloodSugarRepository;
	private final GeminiClient geminiClient;

	public List<BloodSugarWithMealWithMealItemWithFoodDto> analyzeHealth(Accessor accessor) {
		Member member = memberRepository.findByPublicId(accessor.getPublicId())
				.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
		LocalDate now = LocalDate.now();
		List<BloodSugar> bloodSugarList = bloodSugarRepository.findByMemberAndPeriodWithMealAndMealItemAndFood(
				member.getId(),
				now.minusDays(6).atStartOfDay(),
				now.atTime(LocalTime.MAX)
		);
		// return geminiClient.generateContent("ping");
		return bloodSugarList.stream()
				.map(BloodSugarWithMealWithMealItemWithFoodDto::from)
				.collect(Collectors.toList());
	}
}

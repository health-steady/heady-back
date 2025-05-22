package com.heady.headyback.ai.service;

import static com.heady.headyback.member.exception.MemberExceptionCode.*;
import static java.util.stream.Collectors.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.heady.headyback.ai.client.GeminiClient;
import com.heady.headyback.ai.dto.AiAnalysisDto;
import com.heady.headyback.ai.dto.DailyNutrient;
import com.heady.headyback.ai.util.PromptBuilder;
import com.heady.headyback.auth.domain.Accessor;
import com.heady.headyback.bloodSugar.domain.BloodSugar;
import com.heady.headyback.bloodSugar.dto.BloodSugarWithMealDto;
import com.heady.headyback.bloodSugar.dto.BloodSugarWithMealWithMealItemWithFoodDto;
import com.heady.headyback.bloodSugar.repository.BloodSugarRepository;
import com.heady.headyback.common.exception.CustomException;
import com.heady.headyback.meal.domain.Meal;
import com.heady.headyback.meal.domain.Nutrient;
import com.heady.headyback.member.dto.MemberDto;
import com.heady.headyback.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiAnalysisService {

	private final MemberRepository memberRepository;
	private final BloodSugarRepository bloodSugarRepository;
	private final GeminiClient geminiClient;
	private final PromptBuilder promptBuilder;

	public AiAnalysisDto analyzeHealth(Accessor accessor) {
		MemberDto member = memberRepository.findByPublicId(accessor.getPublicId())
				.map(MemberDto::from)
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
						.toList(),
				aggregateDailyNutrients(bloodSugarList)
		);
	}

	/**
	 * 날짜별 총영양소를 집계하고 정렬된 리스트로 반환합니다.
	 *
	 * @param bloodSugars 혈당 기록 목록
	 * @return 날짜별 영양소 합계 리스트
	 */
	private List<DailyNutrient> aggregateDailyNutrients(List<BloodSugar> bloodSugars) {
		Map<LocalDate, Set<Meal>> mealsByDate = bloodSugars.stream()
				.map(BloodSugar::getMeal)
				.filter(Objects::nonNull)
				.collect(groupingBy(
						meal -> meal.getMealDateTime().toLocalDate(),
						toSet()
				));

		return mealsByDate.entrySet().stream()
				.map(entry -> {
					LocalDate date = entry.getKey();
					Nutrient sum = entry.getValue().stream()
							.map(Meal::calculateTotalNutrient)
							.reduce(Nutrient.zero(), Nutrient::add);
					return new DailyNutrient(date, sum);
				})
				.sorted(Comparator.comparing(DailyNutrient::date))
				.toList();
	}
}

package com.heady.headyback.meal.service;

import static com.heady.headyback.member.exception.MemberExceptionCode.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heady.headyback.auth.domain.Accessor;
import com.heady.headyback.bloodSugar.repository.BloodSugarRepository;
import com.heady.headyback.common.exception.CustomException;
import com.heady.headyback.meal.domain.Food;
import com.heady.headyback.meal.domain.FoodEntry;
import com.heady.headyback.meal.domain.Meal;
import com.heady.headyback.meal.domain.Nutrient;
import com.heady.headyback.meal.domain.enumerated.MealType;
import com.heady.headyback.meal.dto.MealDto;
import com.heady.headyback.meal.dto.NutrientSummaryDto;
import com.heady.headyback.meal.dto.request.MealRequest;
import com.heady.headyback.meal.dto.request.MealRequest.FoodInfo;
import com.heady.headyback.meal.repository.FoodRepository;
import com.heady.headyback.meal.repository.MealRepository;
import com.heady.headyback.member.domain.Member;
import com.heady.headyback.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MealService {

	private final MemberRepository memberRepository;
	private final MealRepository mealRepository;
	private final BloodSugarRepository bloodSugarRepository;
	private final FoodRepository foodRepository;

	// TODO : 식사 타입 이미 존재 확인 아침, 점심, 저녁 각 1번씩
	// TODO : 간식은 어떻게?? 혈당이랑 매칭 어떻게? 혈당 등록할 때 API로 식사 목록을 가져오고 선택지를 주기?
	@Transactional
	public MealDto save(Accessor accessor, MealRequest request) {
		Member member = memberRepository.findById(accessor.getId())
				.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

		Meal meal = Meal.ofRecord(
				member,
				MealType.getMappedMealType(request.mealType()),
				request.mealDateTime(),
				getFoodEntries(request.foods()),
				request.memo()
		);

		assignMealToBloodSugars(member.getId(), meal);
		return MealDto.of(mealRepository.save(meal));
	}

	public NutrientSummaryDto getNutrientSummary(Accessor accessor, LocalDate date) {
		Member member = memberRepository.findById(accessor.getId())
				.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

		return NutrientSummaryDto.of(
				summaryNutrient(
						mealRepository.findByMemberIdAndMealDateTimeBetween(
								member.getId(),
								date.atStartOfDay(),
								date.atTime(LocalTime.MAX)
						)
				)
		);
	}

	private void assignMealToBloodSugars(Long memberId, Meal meal) {
		LocalDate date = meal.getMealDateTime().toLocalDate();
		LocalDateTime start = date.atStartOfDay();
		LocalDateTime end = date.atTime(LocalTime.MAX);

		bloodSugarRepository
				.findByMemberIdAndMealTypeAndMeasuredAtBetween(
						memberId,
						meal.getMealType(),
						start,
						end
				).stream()
				.filter(bloodSugar -> bloodSugar.getMeal() == null)
				.forEach(bloodSugar -> bloodSugar.assignToMeal(meal));
	}

	private Nutrient summaryNutrient(List<Meal> meals) {
		BigDecimal totalCarb = BigDecimal.ZERO;
		BigDecimal totalProtein = BigDecimal.ZERO;
		BigDecimal totalFat = BigDecimal.ZERO;

		for (Meal meal : meals) {
			Nutrient n = meal.calculateTotalNutrient();
			totalCarb = totalCarb.add(n.carbohydrate());
			totalProtein = totalProtein.add(n.protein());
			totalFat = totalFat.add(n.fat());
		}

		return Nutrient.of(totalCarb, totalProtein, totalFat);
	}

	private List<FoodEntry> getFoodEntries(List<FoodInfo> foods) {
		return foods.stream()
				.map(fi -> {
					if (fi.code() != null && !fi.code().isBlank()) {
						// DB에 있는 음식이면 실제 조회
						Food food = foodRepository.getReferenceById(fi.code());
						return FoodEntry.of(food, food.getName());
					} else {
						// 직접 입력한 음식
						return FoodEntry.of(null, fi.name());
					}
				})
				.toList();
	}
}

package com.heady.headyback.meal.service;

import static com.heady.headyback.meal.exception.MealExceptionCode.*;
import static com.heady.headyback.member.exception.MemberExceptionCode.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heady.headyback.auth.domain.Accessor;
import com.heady.headyback.bloodSugar.domain.BloodSugar;
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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MealService {

	private final MemberRepository memberRepository;
	private final MealRepository mealRepository;
	private final BloodSugarRepository bloodSugarRepository;
	private final FoodRepository foodRepository;

	@Transactional
	public MealDto recordMeal(Accessor accessor, MealRequest request) {
		Member member = memberRepository.findByPublicId(accessor.getPublicId())
				.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

		MealType mealType = MealType.getMappedMealType(request.mealType());
		validateDuplicateMeal(member.getId(), mealType, request.mealDateTime());

		Meal meal = Meal.ofRecord(
				member,
				mealType,
				request.mealDateTime(),
				getFoodEntries(request.foods()),
				request.memo()
		);

		assignMealToBloodSugars(member.getId(), meal);
		return MealDto.of(mealRepository.save(meal));
	}

	public NutrientSummaryDto getNutrientSummary(Accessor accessor, LocalDate date) {
		Member member = memberRepository.findByPublicId(accessor.getPublicId())
				.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

		return NutrientSummaryDto.of(
				date,
				summaryNutrient(
						mealRepository.findByMemberIdAndMealDateTimeBetween(
								member.getId(),
								date.atStartOfDay(),
								date.atTime(LocalTime.MAX)
						)
				)
		);
	}

	@Transactional
	public void deleteMeal(Accessor accessor, Long mealId) {
		Member member = memberRepository.findByPublicId(accessor.getPublicId())
				.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
		Meal meal = mealRepository.findById(mealId)
				.orElseThrow(() -> new CustomException(MEAL_NOT_FOUND));

		if (!meal.isOwnedBy(member)) {
			throw new CustomException(MEAL_NO_AUTHORIZED);
		}

		meal.getBloodSugars().forEach(BloodSugar::deleteMeal);
		mealRepository.delete(meal);
	}

	private void assignMealToBloodSugars(Long memberId, Meal meal) {
		LocalDate date = meal.getMealDateTime().toLocalDate();
		LocalDateTime start = date.atStartOfDay();
		LocalDateTime end = date.atTime(LocalTime.MAX);

		bloodSugarRepository
				.findByMemberAndMealTypeAndPeriod(
						memberId,
						meal.getMealType(),
						start,
						end
				).stream()
				.filter(bloodSugar -> bloodSugar.getMeal() == null)
				.forEach(bloodSugar -> bloodSugar.assignToMeal(meal));
	}

	private void validateDuplicateMeal(
			Long memberId,
			MealType mealType,
			LocalDateTime mealDateTime
	) {
		LocalDate date = mealDateTime.toLocalDate();
		LocalDateTime startOfDay = date.atStartOfDay();
		LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

		boolean alreadyExists = mealRepository.existsByMemberIdAndMealTypeAndMealDateTimeBetween(
				memberId, mealType, startOfDay, endOfDay
		);

		if (alreadyExists) {
			throw new CustomException(MEAL_ALREADY_RECORDED);
		}
	}

	private Nutrient summaryNutrient(List<Meal> meals) {
		return meals.stream()
				.map(Meal::calculateTotalNutrient)
				.reduce(Nutrient.zero(), Nutrient::add);
	}

	private List<FoodEntry> getFoodEntries(List<FoodInfo> foods) {
		return foods.stream()
				.map(fi -> {
					if (fi.code() != null && !fi.code().isBlank()) {
						Food food = foodRepository.getReferenceById(fi.code());
						return FoodEntry.of(food, food.getName());
					} else {
						return FoodEntry.of(null, fi.name());
					}
				})
				.toList();
	}
}

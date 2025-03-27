package com.heady.headyback.meal.service;

import static com.heady.headyback.member.exception.MemberExceptionCode.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heady.headyback.auth.domain.Accessor;
import com.heady.headyback.bloodSugar.repository.BloodSugarRepository;
import com.heady.headyback.common.exception.CustomException;
import com.heady.headyback.meal.domain.Meal;
import com.heady.headyback.meal.domain.enumerated.MealType;
import com.heady.headyback.meal.dto.MealDto;
import com.heady.headyback.meal.dto.request.MealRequest;
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
				request.foodNames(),
				request.memo()
		);

		assignMealToBloodSugars(member.getId(), meal);
		return MealDto.of(mealRepository.save(meal));
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
}

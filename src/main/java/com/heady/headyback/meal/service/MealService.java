package com.heady.headyback.meal.service;

import static com.heady.headyback.member.exception.MemberExceptionCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heady.headyback.auth.domain.Accessor;
import com.heady.headyback.common.exception.CustomException;
import com.heady.headyback.meal.domain.Meal;
import com.heady.headyback.meal.domain.enumerated.MealType;
import com.heady.headyback.meal.dto.MealDto;
import com.heady.headyback.meal.dto.request.RecordRequest;
import com.heady.headyback.meal.repository.MealRepository;
import com.heady.headyback.member.domain.Member;
import com.heady.headyback.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MealService {

	private final MemberRepository memberRepository;
	private final MealRepository mealRepository;

	@Transactional
	public MealDto record(Accessor accessor, RecordRequest request) {
		Member member = memberRepository.findById(accessor.getId())
				.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
		return MealDto.of(
				mealRepository.save(
						Meal.ofRecord(
								member,
								MealType.getMappedMealType(request.mealType()),
								request.mealDateTime(),
								request.foodNames(),
								request.memo()
						)
				)

		);
	}
}

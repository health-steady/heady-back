package com.heady.headyback.bloodSugar.service;

import static com.heady.headyback.member.exception.MemberExceptionCode.*;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.heady.headyback.auth.domain.Accessor;
import com.heady.headyback.bloodSugar.domain.BloodSugar;
import com.heady.headyback.bloodSugar.domain.enumerated.MeasureType;
import com.heady.headyback.bloodSugar.dto.BloodSugarDto;
import com.heady.headyback.bloodSugar.dto.request.RecordRequest;
import com.heady.headyback.bloodSugar.repository.BloodSugarRepository;
import com.heady.headyback.common.exception.CustomException;
import com.heady.headyback.meal.domain.Meal;
import com.heady.headyback.meal.domain.enumerated.MealType;
import com.heady.headyback.meal.repository.MealRepository;
import com.heady.headyback.member.domain.Member;
import com.heady.headyback.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BloodSugarService {

	private final BloodSugarRepository bloodSugarRepository;
	private final MemberRepository memberRepository;
	private final MealRepository mealRepository;

	public BloodSugarDto record(Accessor accessor, RecordRequest request) {
		Member member = memberRepository.findById(accessor.getId())
				.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

		MeasureType measureType = MeasureType.getMappedMeasureType(
				request.measureType());
		MealType mealType = MealType.getMappedMealType(request.mealType());
		Meal meal = getMeal(member.getId(), request.measuredAt(), measureType, mealType);

		return BloodSugarDto.of(
				bloodSugarRepository.save(
						BloodSugar.ofRecord(
								member,
								meal,
								mealType,
								request.level(),
								request.measuredAt(),
								measureType,
								request.memo()
						)
				)
		);
	}

	public Meal getMeal(
			Long memberId,
			LocalDateTime measuredAt,
			MeasureType measureType,
			MealType mealType
	) {
		if (measureType.requiresMeal()) {
			return mealRepository
					.findByMemberIdAndMealTypeAndMeasuredAt(
							memberId,
							mealType,
							measuredAt
					)
					.orElse(null);
		}

		return null;
	}
}

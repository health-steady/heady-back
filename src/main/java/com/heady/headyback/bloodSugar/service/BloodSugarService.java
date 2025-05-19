package com.heady.headyback.bloodSugar.service;

import static com.heady.headyback.member.exception.MemberExceptionCode.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heady.headyback.auth.domain.Accessor;
import com.heady.headyback.bloodSugar.domain.BloodSugar;
import com.heady.headyback.bloodSugar.domain.enumerated.MeasureType;
import com.heady.headyback.bloodSugar.dto.BloodSugarDto;
import com.heady.headyback.bloodSugar.dto.BloodSugarWithMealDto;
import com.heady.headyback.bloodSugar.dto.BloodSugarSummaryDto;
import com.heady.headyback.bloodSugar.dto.request.BloodSugarRequest;
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

	@Transactional
	public BloodSugarWithMealDto record(Accessor accessor, BloodSugarRequest request) {
		Member member = memberRepository.findByPublicId(accessor.getPublicId())
				.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

		MeasureType measureType = MeasureType.getMappedMeasureType(
				request.measureType());
		MealType mealType = MealType.getMappedMealType(request.mealType());
		Meal meal = getMeal(member.getId(), request.measuredAt(), measureType, mealType);

		return BloodSugarWithMealDto.from(
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

	@Transactional(readOnly = true)
	public List<BloodSugarWithMealDto> getAllByDate(Accessor accessor, LocalDate date) {
		Member member = memberRepository.findByPublicId(accessor.getPublicId())
				.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
		return bloodSugarRepository.findAllByMemberIdAndMeasuredAtBetween(
						member.getId(),
						date.atStartOfDay(),
						date.plusDays(1).atStartOfDay()
				)
				.stream()
				.map(BloodSugarWithMealDto::from)
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<BloodSugarDto> getByDate(Accessor accessor, LocalDate date) {
		Member member = memberRepository.findByPublicId(accessor.getPublicId())
				.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
		return bloodSugarRepository.findByMemberIdAndMeasuredAtBetween(
						member.getId(),
						date.atStartOfDay(),
						date.plusDays(1).atStartOfDay()
				)
				.stream()
				.map(BloodSugarDto::from)
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public BloodSugarSummaryDto getSummaryByDate(Accessor accessor, LocalDate date) {
		Member member = memberRepository.findByPublicId(accessor.getPublicId())
				.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
		return bloodSugarRepository.findSummaryByMemberIdAndMeasuredAtBetween(
				member.getId(),
				date.atStartOfDay(),
				date.plusDays(1).atStartOfDay()
		);
	}

	//TODO : null 처리
	private Meal getMeal(
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

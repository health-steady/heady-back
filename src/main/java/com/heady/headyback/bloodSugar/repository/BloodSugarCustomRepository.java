package com.heady.headyback.bloodSugar.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.heady.headyback.bloodSugar.domain.BloodSugar;
import com.heady.headyback.bloodSugar.dto.BloodSugarSummaryDto;
import com.heady.headyback.meal.domain.enumerated.MealType;

@Repository
public interface BloodSugarCustomRepository {

	List<BloodSugar> findByMemberAndPeriodWithMealAndMealItem(
			Long memberId,
			LocalDateTime start,
			LocalDateTime end
	);

	List<BloodSugar> findByMemberAndMealTypeAndPeriod(
			Long memberId,
			MealType mealType,
			LocalDateTime start,
			LocalDateTime end
	);

	List<BloodSugar> findByMemberAndPeriod(
			Long memberId,
			LocalDateTime start,
			LocalDateTime end
	);

	List<BloodSugar> findByMemberAndPeriodWithMealAndMealItemAndFood(
			Long memberId,
			LocalDateTime start,
			LocalDateTime end
	);

	BloodSugarSummaryDto summarizeByMemberAndPeriod(
			Long memberId,
			LocalDateTime start,
			LocalDateTime end
	);
}

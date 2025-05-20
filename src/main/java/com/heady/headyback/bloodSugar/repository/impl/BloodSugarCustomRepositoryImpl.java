package com.heady.headyback.bloodSugar.repository.impl;

import static com.heady.headyback.bloodSugar.domain.QBloodSugar.*;
import static com.heady.headyback.meal.domain.QFood.*;
import static com.heady.headyback.meal.domain.QMeal.*;
import static com.heady.headyback.meal.domain.QMealItem.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.heady.headyback.bloodSugar.domain.BloodSugar;
import com.heady.headyback.bloodSugar.domain.enumerated.MeasureType;
import com.heady.headyback.bloodSugar.dto.BloodSugarSummaryDto;
import com.heady.headyback.bloodSugar.repository.BloodSugarCustomRepository;
import com.heady.headyback.meal.domain.enumerated.MealType;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BloodSugarCustomRepositoryImpl implements BloodSugarCustomRepository {

	private final JPAQueryFactory queryFactory;

	/**
	 * 특정 회원의 혈당 기록을 기간 내에 조회합니다.
	 * 필요에 따라 연관 엔티티를 Fetch Join하여 한 번에 로딩할 수 있습니다.
	 *
	 * @param memberId 조회할 회원의 ID
	 * @param start    조회 시작 시각
	 * @param end      조회 종료 시각
	 * @return 기간 내 조회된 BloodSugar 엔티티 목록
	 */
	@Override
	public List<BloodSugar> findByMemberAndPeriodWithMealAndMealItem(
			Long memberId,
			LocalDateTime start,
			LocalDateTime end
	) {

		return queryFactory.selectFrom(bloodSugar)
				.leftJoin(bloodSugar.meal, meal).fetchJoin()
				.leftJoin(meal.mealItems, mealItem).fetchJoin()
				.where(
						bloodSugar.member.id.eq(memberId),
						bloodSugar.measuredAt.between(start, end)
				)
				.orderBy(bloodSugar.measuredAt.asc())
				.fetch();
	}

	/**
	 * 특정 회원의 혈당 기록을 기간 내에 조회하되, 식사 종류로 필터링합니다.
	 *
	 * @param memberId 조회할 회원의 ID
	 * @param mealType 조회할 식사 종류 (BREAKFAST, LUNCH, DINNER 등)
	 * @param start    조회 시작 시각
	 * @param end      조회 종료 시각
	 * @return 필터링된 BloodSugar 엔티티 목록
	 */
	@Override
	public List<BloodSugar> findByMemberAndMealTypeAndPeriod(
			Long memberId,
			MealType mealType,
			LocalDateTime start,
			LocalDateTime end
	) {

		return queryFactory
				.selectFrom(bloodSugar)
				.where(
						bloodSugar.member.id.eq(memberId),
						bloodSugar.mealType.eq(mealType),
						bloodSugar.measuredAt.between(start, end)
				)
				.fetch();
	}

	/**
	 * 특정 회원의 혈당 기록을 기간 내에 정렬하여 조회합니다.
	 *
	 * @param memberId 조회할 회원의 ID
	 * @param start    조회 시작 시각
	 * @param end      조회 종료 시각
	 * @return 정렬된 BloodSugar 엔티티 목록
	 */
	@Override
	public List<BloodSugar> findByMemberAndPeriod(
			Long memberId,
			LocalDateTime start,
			LocalDateTime end
	) {

		return queryFactory
				.selectFrom(bloodSugar)
				.where(
						bloodSugar.member.id.eq(memberId),
						bloodSugar.measuredAt.between(start, end)
				)
				.orderBy(bloodSugar.measuredAt.asc())
				.fetch();
	}

	@Override
	public List<BloodSugar> findByMemberAndPeriodWithMealAndMealItemAndFood(
			Long memberId,
			LocalDateTime start,
			LocalDateTime end
	) {
		return queryFactory.selectFrom(bloodSugar)
				.leftJoin(bloodSugar.meal, meal).fetchJoin()
				.leftJoin(meal.mealItems, mealItem).fetchJoin()
				.leftJoin(mealItem.food, food).fetchJoin()
				.where(
						bloodSugar.member.id.eq(memberId),
						bloodSugar.measuredAt.between(start, end)
				)
				.orderBy(bloodSugar.measuredAt.asc())
				.fetch();
	}

	/**
	 * 특정 회원의 혈당 기록을 기간 내에 집계하여 요약 정보를 반환합니다.
	 * CASE 식을 이용해 각 측정 유형별 최대값을 계산합니다.
	 *
	 * @param memberId 조회할 회원의 ID
	 * @param start    조회 시작 시각
	 * @param end      조회 종료 시각
	 * @return BloodSugarSummaryDto 요약 Dto
	 */
	@Override
	public BloodSugarSummaryDto summarizeByMemberAndPeriod(
			Long memberId,
			LocalDateTime start,
			LocalDateTime end
	) {

		NumberExpression<Integer> maxBreakfast = new CaseBuilder()
				.when(bloodSugar.measureType.eq(MeasureType.AFTER_MEAL)
						.and(bloodSugar.mealType.eq(MealType.BREAKFAST)))
				.then(bloodSugar.level)
				.otherwise((Integer)null)
				.max();

		NumberExpression<Integer> maxLunch = new CaseBuilder()
				.when(bloodSugar.measureType.eq(MeasureType.AFTER_MEAL)
						.and(bloodSugar.mealType.eq(MealType.LUNCH)))
				.then(bloodSugar.level)
				.otherwise((Integer)null)
				.max();

		NumberExpression<Integer> maxDinner = new CaseBuilder()
				.when(bloodSugar.measureType.eq(MeasureType.AFTER_MEAL)
						.and(bloodSugar.mealType.eq(MealType.DINNER)))
				.then(bloodSugar.level)
				.otherwise((Integer)null)
				.max();

		NumberExpression<Integer> maxFasting = new CaseBuilder()
				.when(bloodSugar.measureType.eq(MeasureType.FASTING))
				.then(bloodSugar.level)
				.otherwise((Integer)null)
				.max();

		NumberExpression<Integer> maxPostprandial = new CaseBuilder()
				.when(bloodSugar.measureType.eq(MeasureType.AFTER_MEAL))
				.then(bloodSugar.level)
				.otherwise((Integer)null)
				.max();

		return queryFactory
				.select(Projections.constructor(
						BloodSugarSummaryDto.class,
						maxBreakfast,
						maxLunch,
						maxDinner,
						maxFasting,
						maxPostprandial
				))
				.from(bloodSugar)
				.where(
						bloodSugar.member.id.eq(memberId),
						bloodSugar.measuredAt.between(start, end)
				)
				.fetchOne();
	}
}

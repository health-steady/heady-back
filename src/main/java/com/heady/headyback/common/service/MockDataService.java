package com.heady.headyback.common.service;

import static com.heady.headyback.member.exception.MemberExceptionCode.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.heady.headyback.bloodSugar.domain.BloodSugar;
import com.heady.headyback.bloodSugar.domain.enumerated.MeasureType;
import com.heady.headyback.bloodSugar.repository.BloodSugarRepository;
import com.heady.headyback.common.exception.CustomException;
import com.heady.headyback.meal.domain.Food;
import com.heady.headyback.meal.domain.FoodEntry;
import com.heady.headyback.meal.domain.Meal;
import com.heady.headyback.meal.domain.enumerated.MealType;
import com.heady.headyback.meal.repository.FoodRepository;
import com.heady.headyback.meal.repository.MealRepository;
import com.heady.headyback.member.domain.Member;
import com.heady.headyback.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MockDataService {

	private final MemberRepository memberRepository;
	private final FoodRepository foodRepository;
	private final MealRepository mealRepository;
	private final BloodSugarRepository bloodSugarRepository;
	private static final Random RANDOM = new Random();

	public void insertMockDataForToday() {
		Member member = memberRepository.findById(1L)
				.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
		LocalDate now = LocalDate.now();
		List<Meal> mealList = List.of(
				Meal.ofRecord(
						member,
						MealType.BREAKFAST,
						now.atTime(9, 0),
						getFoodEntry(),
						"아침 식사 데이터"
				),
				Meal.ofRecord(
						member,
						MealType.LUNCH,
						now.atTime(13, 0),
						getFoodEntry(),
						"점심 식사 데이터"
				),
				Meal.ofRecord(
						member,
						MealType.DINNER,
						now.atTime(18, 0),
						getFoodEntry(),
						"저녁 식사 데이터"
				),
				Meal.ofRecord(
						member,
						MealType.SNACK,
						now.atTime(21, 0),
						getFoodEntry(),
						"간식 데이터"
				)
		);

		List<BloodSugar> bloodSugarList = List.of(
				BloodSugar.ofRecord(
						member,
						mealList.get(0),
						MealType.BREAKFAST,
						random80To100(),
						now.atTime(8, 0),
						MeasureType.FASTING,
						"공복 혈당"
				),

				BloodSugar.ofRecord(
						member,
						mealList.get(0),
						MealType.BREAKFAST,
						random80To100(),
						now.atTime(10, 0),
						MeasureType.AFTER_MEAL,
						"아침 식후 1시간 혈당"
				),

				BloodSugar.ofRecord(
						member,
						mealList.get(1),
						MealType.LUNCH,
						random80To100(),
						now.atTime(12, 0),
						MeasureType.BEFORE_MEAL,
						"점심 식전 혈당"
				),

				BloodSugar.ofRecord(
						member,
						mealList.get(1),
						MealType.LUNCH,
						random80To100(),
						now.atTime(14, 0),
						MeasureType.AFTER_MEAL,
						"점심 식후 1시간 혈당"
				),

				BloodSugar.ofRecord(
						member,
						mealList.get(2),
						MealType.DINNER,
						random80To100(),
						now.atTime(17, 0),
						MeasureType.BEFORE_MEAL,
						"저녁 식전 혈당"
				),

				BloodSugar.ofRecord(
						member,
						mealList.get(2),
						MealType.DINNER,
						random80To100(),
						now.atTime(19, 0),
						MeasureType.AFTER_MEAL,
						"저녁 식후 1시간 혈당"
				),

				BloodSugar.ofRecord(
						member,
						mealList.get(3),
						MealType.SNACK,
						random80To100(),
						now.atTime(20, 0),
						MeasureType.BEFORE_MEAL,
						"간식 식전 혈당"
				),

				BloodSugar.ofRecord(
						member,
						mealList.get(3),
						MealType.SNACK,
						random80To100(),
						now.atTime(22, 0),
						MeasureType.AFTER_MEAL,
						"간식 식후 1시간 혈당"
				),

				BloodSugar.ofRecord(
						member,
						null,
						MealType.NONE,
						random80To100(),
						now.atTime(23, 0),
						MeasureType.BEDTIME,
						"취침 전 혈당"
				)
		);

		mealRepository.saveAll(mealList);
		bloodSugarRepository.saveAll(bloodSugarList);
	}

	private List<FoodEntry> getFoodEntry() {
		List<String> foodCodes = List.of(
				"P108-009000400-0125", "P108-009000400-0062",
				"P108-009000400-0094", "P108-009000400-0096",
				"P108-009000400-0098", "P108-009000400-0099",
				"P108-009000400-0100", "P108-009000400-0101",
				"P108-009000400-0102", "P108-009000400-0103",
				"P108-009000400-0071", "P108-009000400-0073",
				"P108-009000400-0074", "P108-009000400-0075",
				"P108-009000400-0064", "P108-003000400-0284",
				"P108-003000400-0267", "P108-009000400-0069",
				"P108-009000400-0013", "P108-009000400-0067"
		);

		List<String> randomTwo = pickTwoRandom(foodCodes);
		return randomTwo.stream()
				.map(
						code -> {
							Food food = foodRepository.getReferenceById(code);
							return FoodEntry.of(food, food.getName());
						}
				)
				.toList();
	}

	private static <T> List<T> pickTwoRandom(List<T> items) {
		if (items.size() < 2) {
			return new ArrayList<>(items);
		}
		List<T> copy = new ArrayList<>(items);
		Collections.shuffle(copy, RANDOM);
		return copy.subList(0, 2);
	}

	/**
	 * 80부터 100(포함) 사이의 정수를 랜덤으로 반환합니다.
	 */
	public static int random80To100() {
		// nextInt(최소, 최대Exclusive) 이므로 최대값에 +1 해줍니다.
		return ThreadLocalRandom.current().nextInt(80, 101);
	}

	/**
	 * 110부터 160(포함) 사이의 정수를 랜덤으로 반환합니다.
	 */
	public static int random110To160() {
		return ThreadLocalRandom.current().nextInt(110, 161);
	}

}

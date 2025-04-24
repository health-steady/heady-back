package com.heady.headyback.meal.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.heady.headyback.meal.domain.enumerated.MealType;
import com.heady.headyback.member.domain.Member;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;

@Table(name = "meals")
@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Meal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Member member;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private MealType mealType;

	@Column(nullable = false)
	private LocalDateTime mealDateTime;

	private String memo;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	@OneToMany(mappedBy = "meal", cascade = CascadeType.ALL)
	private List<MealItem> mealItems = new ArrayList<>();

	public static Meal ofRecord(
			Member member,
			MealType mealType,
			LocalDateTime mealDateTime,
			List<String> foodNames,
			String memo
	) {
		Meal meal = new Meal();
		meal.member = member;
		meal.mealType = mealType;
		meal.mealDateTime = mealDateTime;
		meal.memo = memo;
		addFoods(meal, foodNames);
		return meal;
	}

	public Nutrient calculateTotalNutrient() {
		BigDecimal carbohydrate = BigDecimal.ZERO;
		BigDecimal protein = BigDecimal.ZERO;
		BigDecimal fat = BigDecimal.ZERO;

		for (MealItem mealItem : mealItems) {
			carbohydrate = carbohydrate.add(defaultIfNull(mealItem.getFood().getCarbohydrates()));
			protein = protein.add(defaultIfNull(mealItem.getFood().getProtein()));
			fat = fat.add(defaultIfNull(mealItem.getFood().getFat()));
		}

		return Nutrient.of(carbohydrate, protein, fat);
	}

	private static void addFoods(Meal meal, List<String> foods) {
		// TODO 식품영양성분 DB 활용
		meal.mealItems = new ArrayList<>();
	}

	private BigDecimal defaultIfNull(BigDecimal value) {
		return value != null ? value : BigDecimal.ZERO;
	}
}

package com.heady.headyback.meal.domain;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class MealItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "meal_id", nullable = false)
	private Meal meal;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "food_code")
	private Food food;

	@Column(nullable = false)
	private String name;

	public static MealItem ofAdd(
			Meal meal,
			Food food,
			String name
	) {
		MealItem mealItem = new MealItem();
		mealItem.meal = meal;
		mealItem.name = name;
		mealItem.food = food;
		return mealItem;
	}
}

package com.heady.headyback.meal.repository.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Repository;

import com.heady.headyback.meal.domain.Food;
import com.heady.headyback.meal.domain.QFood;
import com.heady.headyback.meal.repository.FoodCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FoodCustomRepositoryImpl implements FoodCustomRepository {

	private final JPAQueryFactory queryFactory;
	private final QFood food = QFood.food;

	@Override
	public List<Food> searchByKeyword(String keyword, long offset, long limit) {
		return queryFactory
				.selectFrom(food)
				.where(food.name.containsIgnoreCase(keyword))
				.orderBy(food.name.asc())
				.offset(offset)
				.limit(limit)
				.fetch();
	}

	@Override
	public long countByKeyword(String keyword) {
		Long count = queryFactory
				.select(food.count().coalesce(0L))
				.from(food)
				.where(food.name.containsIgnoreCase(keyword))
				.fetchOne();

		return Objects.requireNonNullElse(count, 0L);
	}
}

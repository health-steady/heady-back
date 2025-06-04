package com.heady.headyback.meal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.heady.headyback.meal.domain.Food;

@Repository
public interface FoodRepository extends JpaRepository<Food, String>, FoodCustomRepository {

	/**
	 * 키워드 기반으로 음식 데이터를 검색하고 결과를 페이지네이션하여 반환
	 */
	@Query(value = """
			SELECT *
			FROM foods
			WHERE MATCH(name) AGAINST(:keyword IN NATURAL LANGUAGE MODE)
			ORDER BY MATCH(name) AGAINST(:keyword IN NATURAL LANGUAGE MODE) DESC
			LIMIT :offset, :limit
			""", nativeQuery = true)
	List<Food> searchByKeyword(
			@Param("keyword") String keyword,
			@Param("offset") long offset,
			@Param("limit") long limit
	);

	/**
	 * 주어진 키워드에 대해 검색 조건을 만족하는 총 행 수를 반환
	 */
	@Query(value = """
			SELECT COUNT(*)
			FROM foods
			WHERE MATCH(name) AGAINST(:keyword IN NATURAL LANGUAGE MODE)
			""", nativeQuery = true)
	long countByKeyword(@Param("keyword") String keyword);
}

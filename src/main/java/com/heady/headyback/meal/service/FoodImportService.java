package com.heady.headyback.meal.service;

import java.net.URI;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heady.headyback.common.util.WebClientUtil;
import com.heady.headyback.meal.domain.Food;
import com.heady.headyback.meal.dto.response.FoodApiResponse;
import com.heady.headyback.meal.repository.FoodRepository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoodImportService {

	private final FoodRepository foodRepository;
	private final WebClientUtil webClientUtil;
	private final EntityManager entityManager;
	private final int pageSize = 500;

	/**
	 * 공공 API 전체 데이터를 페이지 단위로 가져와서 저장
	 * @return 저장된 총 건수
	 */
	@Transactional
	public int importAllFoods() {
		int pageNo = 340;
		int totalImported = 0;
		int totalPageNo = getTotalPageNo();

		for (int i = pageNo; i <= totalPageNo; i++) {
			URI uri = webClientUtil.buildRequestUri(pageNo, pageSize);
			FoodApiResponse response = webClientUtil.get(uri, FoodApiResponse.class);
			List<FoodApiResponse.FoodItem> items = response
					.response()
					.body()
					.items();
			if (items == null || items.isEmpty()) {
				break;
			}

			List<Food> foods = items.stream()
					.map(item -> Food.ofRecord(
							item.code(),
							item.name(),
							item.manufacturerName(),
							item.calories(),
							item.carbohydrates(),
							item.sugar(),
							item.fiber(),
							item.protein(),
							item.fat(),
							item.satFat(),
							item.transFat(),
							item.sodium()
					))
					.toList();

			foodRepository.saveAll(foods);
			totalImported += foods.size();

			entityManager.flush();
			entityManager.clear();

			log.info("▶ totalImported = {}", totalImported);
			log.info("▶ pageNo = = {}", pageNo);

			pageNo++;
		}
		return totalImported;
	}

	public int getTotalPageNo() {
		URI firstUri = webClientUtil.buildRequestUri(1, pageSize);
		FoodApiResponse firstResp = webClientUtil.get(firstUri, FoodApiResponse.class);
		var firstBody = firstResp.response().body();

		int totalCount = firstBody.totalCount();
		int numOfRows = firstBody.numOfRows();
		return (totalCount + numOfRows - 1) / numOfRows;
	}
}

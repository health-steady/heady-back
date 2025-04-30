package com.heady.headyback.meal.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heady.headyback.meal.domain.Food;
import com.heady.headyback.meal.dto.FoodDto;
import com.heady.headyback.meal.repository.FoodRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoodService {

	private final FoodRepository foodRepository;

	@Transactional(readOnly = true)
	public Page<FoodDto> searchFoods(
			String keyword,
			int pageNo,
			int pageSize
	) {
		long offset = (long)(pageNo - 1) * pageSize;
		List<Food> content = foodRepository.searchByKeyword(keyword, offset, pageSize);
		long total = foodRepository.countByKeyword(keyword);
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return new PageImpl<>(content, pageable, total).map(FoodDto::from);
	}

}

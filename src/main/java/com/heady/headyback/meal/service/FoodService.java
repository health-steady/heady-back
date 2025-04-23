package com.heady.headyback.meal.service;

import org.springframework.stereotype.Service;

import com.heady.headyback.meal.repository.FoodRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FoodService {

	private final FoodRepository foodRepository;

}

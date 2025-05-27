package com.heady.headyback.common.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.heady.headyback.common.service.MockDataService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MockDataScheduler {

	private final MockDataService mockDataService;
	/**
	 * 매일 오전 1시 (Asia/Seoul) 에 mock data 삽입
	 */
	@Scheduled(cron = "0 0 1 * * *", zone = "Asia/Seoul")
	public void runDailyMockData() {
		mockDataService.insertMockDataForToday();
	}
}

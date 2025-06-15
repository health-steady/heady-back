package com.heady.headyback.bloodSugar;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heady.headyback.auth.domain.Accessor;
import com.heady.headyback.auth.domain.enumerated.Authority;
import com.heady.headyback.auth.jwt.JwtResolver;
import com.heady.headyback.auth.service.AuthService;
import com.heady.headyback.bloodSugar.controller.BloodSugarController;
import com.heady.headyback.bloodSugar.domain.enumerated.MeasureType;
import com.heady.headyback.bloodSugar.dto.BloodSugarDto;
import com.heady.headyback.bloodSugar.dto.BloodSugarSummaryDto;
import com.heady.headyback.bloodSugar.dto.BloodSugarWithMealDto;
import com.heady.headyback.bloodSugar.dto.request.BloodSugarRequest;
import com.heady.headyback.bloodSugar.service.BloodSugarService;
import com.heady.headyback.meal.domain.enumerated.MealType;
import com.heady.headyback.meal.dto.MealDto;
import com.heady.headyback.meal.dto.MealItemDto;

@WebMvcTest(BloodSugarController.class)
public class BloodControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockitoBean
	private AuthService authService;

	@MockitoBean
	private JwtResolver jwtResolver;

	@MockitoBean
	private BloodSugarService bloodSugarService;

	private Accessor accessor;

	@BeforeEach
	void setUp() {
		accessor = new Accessor(UUID.randomUUID(), Authority.MEMBER);
	}

	@Test
	@DisplayName("혈당 기록을 정상적으로 저장한다.")
	void recordBloodSugar() throws Exception {
		BloodSugarRequest request = new BloodSugarRequest(
				LocalDateTime.of(2025, 6, 15, 8, 30),
				"FASTING",
				"BREAKFAST",
				110,
				"공복 측정 테스트"
		);

		MealItemDto item1 = new MealItemDto(1L, "현미밥");
		MealItemDto item2 = new MealItemDto(2L, "불고기");

		MealDto mealDto = new MealDto(
				101L,
				MealType.DINNER,
				LocalDateTime.of(2025, 6, 15, 18, 0),
				"저녁 식사 예시",
				Set.of(item1, item2)
		);

		BloodSugarWithMealDto dto = new BloodSugarWithMealDto(
				1001L,
				105,
				LocalDateTime.of(2025, 6, 15, 7, 45),
				MeasureType.FASTING,
				"아침 공복 측정",
				MealType.BREAKFAST,
				mealDto
		);

		when(bloodSugarService.record(any(), any())).thenReturn(dto);

		mvc.perform(post("/api/blood-sugars/v1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request))
						.requestAttr("accessor", accessor))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1001L))
				.andExpect(jsonPath("$.level").value(105))
				.andExpect(jsonPath("$.measuredAt").value("2025-06-15T07:45:00"))
				.andExpect(jsonPath("$.measureType").value("FASTING"))
				.andExpect(jsonPath("$.memo").value("아침 공복 측정"))
				.andExpect(jsonPath("$.mealType").value("BREAKFAST"))
				.andExpect(jsonPath("$.meal.foods.[0].name").value("현미밥"))
				.andExpect(jsonPath("$.meal.foods.[1].name").value("불고기"));
	}

	@Test
	@DisplayName("특정 날짜의 모든 혈당 기록을 조회한다.")
	void getAllBloodSugarsByDate() throws Exception {
		MealItemDto item1 = new MealItemDto(1L, "현미밥");
		MealItemDto item2 = new MealItemDto(2L, "불고기");

		MealDto mealDto = new MealDto(
				101L,
				MealType.DINNER,
				LocalDateTime.of(2025, 6, 15, 18, 0),
				"저녁 식사 예시",
				Set.of(item1, item2)
		);

		BloodSugarWithMealDto dto = new BloodSugarWithMealDto(
				1001L,
				105,
				LocalDateTime.of(2025, 6, 15, 7, 45),
				MeasureType.FASTING,
				"아침 공복 측정",
				MealType.BREAKFAST,
				mealDto
		);

		when(bloodSugarService.getAllByDate(any(), any())).thenReturn(List.of(dto));

		mvc.perform(get("/api/blood-sugars/v1")
						.param("date", "2025-06-01")
						.requestAttr("accessor", accessor))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(1001L))
				.andExpect(jsonPath("$[0].level").value(105))
				.andExpect(jsonPath("$[0].meal.foods.[0].name").value("현미밥"))
				.andExpect(jsonPath("$[0].meal.foods.[1].name").value("불고기"));
	}

	@Test
	@DisplayName("날짜별 혈당 기록을 조회한다.")
	void getBloodSugarByDate() throws Exception {
		BloodSugarDto dto = new BloodSugarDto(
				3L,
				130,
				LocalDateTime.of(2025, 6, 1, 18, 30),
				MeasureType.AFTER_MEAL,
				"저녁 기록",
				MealType.DINNER
		);

		when(bloodSugarService.getByDate(any(), any())).thenReturn(List.of(dto));

		mvc.perform(get("/api/blood-sugars/v1/2025-06-01")
						.requestAttr("accessor", accessor))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(3L))
				.andExpect(jsonPath("$[0].level").value(130))
				.andExpect(jsonPath("$[0].measureType").value("AFTER_MEAL"))
				.andExpect(jsonPath("$[0].memo").value("저녁 기록"));
	}

	@Test
	@DisplayName("혈당 요약 정보를 조회한다.")
	void getBloodSugarSummary() throws Exception {
		// given
		BloodSugarSummaryDto summaryDto = new BloodSugarSummaryDto(
				95, 110, 130, 140, 180
		);

		when(bloodSugarService.getSummaryByDate(any(), any()))
				.thenReturn(summaryDto);

		// when & then
		mvc.perform(get("/api/blood-sugars/v1/summary")
						.param("date", "2025-06-01")
						.requestAttr("accessor", accessor)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.breakfast").value(95))
				.andExpect(jsonPath("$.lunch").value(110))
				.andExpect(jsonPath("$.dinner").value(130))
				.andExpect(jsonPath("$.highestFasting").value(140))
				.andExpect(jsonPath("$.highestPostprandial").value(180));
	}

	@Test
	@DisplayName("혈당 기록을 ID로 삭제한다.")
	void deleteBloodSugar() throws Exception {
		doNothing().when(bloodSugarService).delete(any(), any());

		mvc.perform(delete("/api/blood-sugars/v1/{id}", 1L)
						.requestAttr("accessor", accessor))
				.andExpect(status().isNoContent());
	}
}

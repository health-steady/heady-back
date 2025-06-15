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
public class BloodSugarControllerTest {

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

	private static final UUID TEST_UUID = UUID.fromString("11111111-1111-1111-1111-111111111111");

	private static final String TEST_MEMO = "공복 측정 테스트";
	private static final String RESPONSE_MEMO = "아침 공복 측정";
	private static final String EVENING_MEMO = "저녁 기록";
	private static final String MEAL_MEMO = "저녁 식사 예시";

	private static final String MEAL_NAME_1 = "현미밥";
	private static final String MEAL_NAME_2 = "불고기";

	private static final String MEASURE_TYPE_STR = "FASTING";
	private static final String MEAL_TYPE_STR = "BREAKFAST";
	private static final String TEST_AFTER_MEAL = "AFTER_MEAL";

	private static final String TEST_DATE_STR = "2025-06-01";
	private static final LocalDateTime REQUEST_MEASURED_AT = LocalDateTime.of(2025, 6, 15, 8, 30);
	private static final LocalDateTime RESPONSE_MEASURED_AT = LocalDateTime.of(2025, 6, 15, 7, 45);
	private static final LocalDateTime DINNER_AT = LocalDateTime.of(2025, 6, 15, 18, 0);
	private static final LocalDateTime EVENING_MEASURED_AT = LocalDateTime.of(2025, 6, 1, 18, 30);

	private static final long BLOOD_SUGAR_ID = 1001L;
	private static final long MEAL_ID = 101L;
	private static final long MEAL_ITEM_ID_1 = 1L;
	private static final long MEAL_ITEM_ID_2 = 2L;

	private static final int REQUEST_LEVEL = 110;
	private static final int RESPONSE_LEVEL = 105;
	private static final int EVENING_LEVEL = 130;

	private static final int SUMMARY_BREAKFAST = 95;
	private static final int SUMMARY_LUNCH = 110;
	private static final int SUMMARY_DINNER = 130;
	private static final int SUMMARY_FASTING = 140;
	private static final int SUMMARY_POSTPRANDIAL = 180;

	@BeforeEach
	void setUp() {
		accessor = new Accessor(TEST_UUID, Authority.MEMBER);
	}

	@Test
	@DisplayName("혈당 기록을 정상적으로 저장한다.")
	void recordBloodSugar() throws Exception {
		BloodSugarRequest request = new BloodSugarRequest(
				REQUEST_MEASURED_AT,
				MEASURE_TYPE_STR,
				MEAL_TYPE_STR,
				REQUEST_LEVEL,
				TEST_MEMO
		);

		Set<MealItemDto> mealItems = Set.of(
				new MealItemDto(MEAL_ITEM_ID_1, MEAL_NAME_1),
				new MealItemDto(MEAL_ITEM_ID_2, MEAL_NAME_2)
		);

		MealDto mealDto = new MealDto(
				MEAL_ID, MealType.DINNER, DINNER_AT, MEAL_MEMO, mealItems
		);

		BloodSugarWithMealDto dto = new BloodSugarWithMealDto(
				BLOOD_SUGAR_ID,
				RESPONSE_LEVEL,
				RESPONSE_MEASURED_AT,
				MeasureType.FASTING,
				RESPONSE_MEMO,
				MealType.BREAKFAST,
				mealDto
		);

		when(bloodSugarService.record(any(), any())).thenReturn(dto);

		mvc.perform(post("/api/blood-sugars/v1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request))
						.requestAttr("accessor", accessor))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(BLOOD_SUGAR_ID))
				.andExpect(jsonPath("$.level").value(RESPONSE_LEVEL))
				.andExpect(jsonPath("$.measureType").value(MEASURE_TYPE_STR))
				.andExpect(jsonPath("$.memo").value(RESPONSE_MEMO))
				.andExpect(jsonPath("$.mealType").value(MEAL_TYPE_STR))
				.andExpect(jsonPath("$.meal.foods.[0].name").value(MEAL_NAME_1))
				.andExpect(jsonPath("$.meal.foods.[1].name").value(MEAL_NAME_2));
	}

	@Test
	@DisplayName("특정 날짜의 모든 혈당 기록을 조회한다.")
	void getAllBloodSugarsByDate() throws Exception {
		MealDto mealDto = new MealDto(
				MEAL_ID,
				MealType.DINNER,
				DINNER_AT,
				MEAL_MEMO,
				Set.of(
						new MealItemDto(MEAL_ITEM_ID_1, MEAL_NAME_1),
						new MealItemDto(MEAL_ITEM_ID_2, MEAL_NAME_2)
				)
		);

		BloodSugarWithMealDto dto = new BloodSugarWithMealDto(
				BLOOD_SUGAR_ID,
				RESPONSE_LEVEL,
				RESPONSE_MEASURED_AT,
				MeasureType.FASTING,
				RESPONSE_MEMO,
				MealType.BREAKFAST,
				mealDto
		);

		when(bloodSugarService.getAllByDate(any(), any())).thenReturn(List.of(dto));

		mvc.perform(get("/api/blood-sugars/v1")
						.param("date", TEST_DATE_STR)
						.requestAttr("accessor", accessor))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(BLOOD_SUGAR_ID))
				.andExpect(jsonPath("$[0].level").value(RESPONSE_LEVEL))
				.andExpect(jsonPath("$[0].meal.foods.[0].name").value(MEAL_NAME_1))
				.andExpect(jsonPath("$[0].meal.foods.[1].name").value(MEAL_NAME_2));
	}

	@Test
	@DisplayName("날짜별 혈당 기록을 조회한다.")
	void getBloodSugarByDate() throws Exception {
		BloodSugarDto dto = new BloodSugarDto(
				3L,
				EVENING_LEVEL,
				EVENING_MEASURED_AT,
				MeasureType.AFTER_MEAL,
				EVENING_MEMO,
				MealType.DINNER
		);

		when(bloodSugarService.getByDate(any(), any())).thenReturn(List.of(dto));

		mvc.perform(get("/api/blood-sugars/v1/" + TEST_DATE_STR)
						.requestAttr("accessor", accessor))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(3L))
				.andExpect(jsonPath("$[0].level").value(EVENING_LEVEL))
				.andExpect(jsonPath("$[0].measureType").value(TEST_AFTER_MEAL))
				.andExpect(jsonPath("$[0].memo").value(EVENING_MEMO));
	}

	@Test
	@DisplayName("혈당 요약 정보를 조회한다.")
	void getBloodSugarSummary() throws Exception {
		BloodSugarSummaryDto summaryDto = new BloodSugarSummaryDto(
				SUMMARY_BREAKFAST,
				SUMMARY_LUNCH,
				SUMMARY_DINNER,
				SUMMARY_FASTING,
				SUMMARY_POSTPRANDIAL
		);

		when(bloodSugarService.getSummaryByDate(any(), any()))
				.thenReturn(summaryDto);

		mvc.perform(get("/api/blood-sugars/v1/summary")
						.param("date", TEST_DATE_STR)
						.requestAttr("accessor", accessor))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.breakfast").value(SUMMARY_BREAKFAST))
				.andExpect(jsonPath("$.lunch").value(SUMMARY_LUNCH))
				.andExpect(jsonPath("$.dinner").value(SUMMARY_DINNER))
				.andExpect(jsonPath("$.highestFasting").value(SUMMARY_FASTING))
				.andExpect(jsonPath("$.highestPostprandial").value(SUMMARY_POSTPRANDIAL));
	}

	@Test
	@DisplayName("혈당 기록을 ID로 삭제한다.")
	void deleteBloodSugar() throws Exception {
		doNothing().when(bloodSugarService).delete(any(), any());

		mvc.perform(delete("/api/blood-sugars/v1/{id}", BLOOD_SUGAR_ID)
						.requestAttr("accessor", accessor))
				.andExpect(status().isNoContent());
	}
}

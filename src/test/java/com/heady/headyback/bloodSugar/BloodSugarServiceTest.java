package com.heady.headyback.bloodSugar;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.heady.headyback.auth.domain.Accessor;
import com.heady.headyback.auth.domain.enumerated.Authority;
import com.heady.headyback.bloodSugar.domain.BloodSugar;
import com.heady.headyback.bloodSugar.dto.BloodSugarSummaryDto;
import com.heady.headyback.bloodSugar.dto.BloodSugarWithMealDto;
import com.heady.headyback.bloodSugar.dto.request.BloodSugarRequest;
import com.heady.headyback.bloodSugar.repository.BloodSugarRepository;
import com.heady.headyback.bloodSugar.service.BloodSugarService;
import com.heady.headyback.common.exception.CustomException;
import com.heady.headyback.meal.repository.MealRepository;
import com.heady.headyback.member.domain.Member;
import com.heady.headyback.member.exception.MemberExceptionCode;
import com.heady.headyback.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
public class BloodSugarServiceTest {

	@Mock
	private BloodSugarRepository bloodSugarRepository;
	@Mock
	private MemberRepository memberRepository;
	@Mock
	private MealRepository mealRepository;

	@InjectMocks
	private BloodSugarService bloodSugarService;

	private Member member;
	private Accessor accessor;

	private static final String TEST_EMAIL = "test@example.com";
	private static final String TEST_PASSWORD = "password123!";
	private static final String TEST_NAME = "테스트회원";
	private static final String TEST_BIRTH = "19900101";
	private static final String TEST_GENDER = "MALE";
	private static final String TEST_PHONE = "01012345678";
	private static final String TEST_MEAL_TYPE = "BREAKFAST";
	private static final String TEST_MEASURE_TYPE = "FASTING";
	private static final String TEST_MEMO = "공복 측정 테스트";
	private static final int TEST_LEVEL = 110;
	private static final int TEST_SUMMARY_BREAKFAST = 90;
	private static final int TEST_SUMMARY_LUNCH = 120;
	private static final int TEST_SUMMARY_DINNER = 130;
	private static final int TEST_SUMMARY_FASTING = 145;
	private static final int TEST_SUMMARY_POST = 180;
	private static final LocalDateTime TEST_MEASURED_AT = LocalDateTime.of(2025, 6, 15, 8, 30);
	private static final LocalDate TEST_DATE = LocalDate.of(2025, 6, 15);
	private static final long BLOOD_SUGAR_ID = 1L;

	@BeforeEach
	void setUp() {
		this.member = Member.ofRegister(
				TEST_EMAIL,
				TEST_PASSWORD,
				TEST_NAME,
				TEST_BIRTH,
				TEST_GENDER,
				TEST_PHONE
		);
		UUID testUuid = UUID.randomUUID();
		member.setPublicIdForTest(testUuid);
		this.accessor = new Accessor(testUuid, Authority.MEMBER);
	}

	@Test
	@DisplayName("혈당 기록 저장 - 정상 케이스")
	void recordBloodSugar_success() {
		// given
		BloodSugarRequest request = new BloodSugarRequest(
				TEST_MEASURED_AT,
				TEST_MEASURE_TYPE,
				TEST_MEAL_TYPE,
				TEST_LEVEL,
				TEST_MEMO
		);

		given(memberRepository.findByPublicId(accessor.getPublicId()))
				.willReturn(Optional.of(member));
		given(bloodSugarRepository.save(any(BloodSugar.class)))
				.willAnswer(invocation -> invocation.getArgument(0));

		// when
		BloodSugarWithMealDto result = bloodSugarService.record(accessor, request);

		// then
		assertThat(result).isNotNull();
		assertThat(result.level()).isEqualTo(TEST_LEVEL);
	}

	@Test
	@DisplayName("요약 정보 조회 - 정상 케이스")
	void getSummaryByDate_success() {
		// given
		BloodSugarSummaryDto dto = new BloodSugarSummaryDto(
				TEST_SUMMARY_BREAKFAST,
				TEST_SUMMARY_LUNCH,
				TEST_SUMMARY_DINNER,
				TEST_SUMMARY_FASTING,
				TEST_SUMMARY_POST
		);

		given(memberRepository.findByPublicId(any())).willReturn(Optional.of(member));
		given(bloodSugarRepository.summarizeByMemberAndPeriod(any(), any(), any()))
				.willReturn(dto);

		// when
		BloodSugarSummaryDto result = bloodSugarService.getSummaryByDate(accessor, TEST_DATE);

		// then
		assertThat(result.breakfast()).isEqualTo(TEST_SUMMARY_BREAKFAST);
		assertThat(result.highestPostprandial()).isEqualTo(TEST_SUMMARY_POST);
	}

	@Test
	@DisplayName("혈당 조회 - Member가 없을 경우 예외 발생")
	void getByDate_memberNotFound() {
		given(memberRepository.findByPublicId(any())).willReturn(Optional.empty());

		assertThatThrownBy(() ->
				bloodSugarService.getByDate(accessor, LocalDate.now())
		)
				.isInstanceOf(CustomException.class)
				.satisfies(e -> {
					CustomException ce = (CustomException)e;
					assertThat(ce.getExceptionCode()).isEqualTo(
							MemberExceptionCode.MEMBER_NOT_FOUND);
					assertThat(ce.getMessage()).isEqualTo(e.getMessage());
				});
	}

	@Test
	@DisplayName("혈당 삭제 - 권한 없을 경우 예외 발생")
	void deleteBloodSugar_unauthorized() {
		BloodSugar bloodSugar = mock(BloodSugar.class);
		given(memberRepository.findByPublicId(any())).willReturn(Optional.of(member));
		given(bloodSugarRepository.findById(anyLong())).willReturn(Optional.of(bloodSugar));
		given(bloodSugar.isOwnedBy(any(Member.class))).willReturn(false);

		assertThatThrownBy(() ->
				bloodSugarService.delete(accessor, BLOOD_SUGAR_ID)
		).isInstanceOf(CustomException.class);
	}

	@Test
	@DisplayName("혈당 삭제 - 정상 케이스")
	void deleteBloodSugar_success() {
		BloodSugar bloodSugar = mock(BloodSugar.class);
		given(memberRepository.findByPublicId(any())).willReturn(Optional.of(member));
		given(bloodSugarRepository.findById(anyLong())).willReturn(Optional.of(bloodSugar));
		given(bloodSugar.isOwnedBy(any(Member.class))).willReturn(true);
		willDoNothing().given(bloodSugarRepository).delete(any());

		bloodSugarService.delete(accessor, BLOOD_SUGAR_ID);
	}
}

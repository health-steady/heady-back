package com.heady.headyback.bloodSugar.service;

import static com.heady.headyback.member.exception.MemberExceptionCode.*;

import org.springframework.stereotype.Service;

import com.heady.headyback.auth.domain.Accessor;
import com.heady.headyback.bloodSugar.domain.BloodSugar;
import com.heady.headyback.bloodSugar.dto.BloodSugarDto;
import com.heady.headyback.bloodSugar.dto.request.RecordRequest;
import com.heady.headyback.bloodSugar.repository.BloodSugarRepository;
import com.heady.headyback.common.exception.CustomException;
import com.heady.headyback.member.domain.Member;
import com.heady.headyback.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BloodSugarService {

	private final BloodSugarRepository bloodSugarRepository;
	private final MemberRepository memberRepository;

	public BloodSugarDto record(Accessor accessor, RecordRequest request) {
		Member member = memberRepository.findById(accessor.getId())
				.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

		return BloodSugarDto.of(
				bloodSugarRepository.save(
						BloodSugar.ofRecord(
								member,
								null,
								request.level(),
								request.measuredAt(),
								request.measurementTimeType(),
								request.memo()
						)
				)
		);
	}
}

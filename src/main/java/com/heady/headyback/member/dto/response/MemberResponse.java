package com.heady.headyback.member.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.heady.headyback.member.dto.MemberDto;

public record MemberResponse(
		Long id,
		TargetResponse target,
		String email,
		String name,
		String nickname,
		LocalDate birthdate,
		String gender,
		BigDecimal height,
		BigDecimal weight,
		String profileImageUrl,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
) {
	public static MemberResponse from(MemberDto memberDto) {
		return new MemberResponse(
				memberDto.id(),
				TargetResponse.of(memberDto.targetDto()),
				memberDto.email(),
				memberDto.name(),
				memberDto.nickname(),
				memberDto.birthdate(),
				memberDto.gender(),
				memberDto.height(),
				memberDto.weight(),
				memberDto.profileImageUrl(),
				memberDto.createdAt(),
				memberDto.updatedAt()
		);
	}
}

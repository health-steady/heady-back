package com.heady.headyback.member.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.heady.headyback.member.domain.Member;

public record MemberDto(
		Long id,
		TargetDto targetDto,
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
	public static MemberDto from(Member member) {
		return new MemberDto(
				member.getId(),
				TargetDto.of(member.getTarget()),
				member.getEmail().getValue(),
				member.getName(),
				member.getNickname(),
				member.getBirthdate(),
				member.getGender().getTitle(),
				member.getHeight(),
				member.getWeight(),
				member.getProfileImageUrl(),
				member.getCreatedAt(),
				member.getUpdatedAt()
		);
	}
}

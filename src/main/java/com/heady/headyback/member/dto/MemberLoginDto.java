package com.heady.headyback.member.dto;

import java.util.UUID;

public record MemberLoginDto(
		UUID publicId,
		String password
) {
}

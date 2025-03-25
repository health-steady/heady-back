package com.heady.headyback.auth.dto.request;

import static com.heady.headyback.member.exception.MemberErrorMessage.*;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
		@NotBlank(message = EMAIL_NOT_BLANK)
		String email,
		@NotBlank(message = PASSWORD_NOT_BLACK)
		String password
) {
}

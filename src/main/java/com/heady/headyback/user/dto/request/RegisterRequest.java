package com.heady.headyback.user.dto.request;

import com.heady.headyback.user.exception.MemberErrorMessage;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
		// TODO : validation
		@Email
		@NotBlank(message = MemberErrorMessage.EMAIL_NOT_BLANK)
		String email,
		@NotBlank(message = MemberErrorMessage.PASSWORD_NOT_BLACK)
		String password,
		@NotBlank(message = MemberErrorMessage.NAME_NOT_BLANK)
		String name,
		@NotBlank(message = MemberErrorMessage.BIRTHDATE_NOT_BLANK)
		String birthdate,
		@NotBlank(message = MemberErrorMessage.GENDER_NOT_BLANK)
		String gender,
		String phone
) {
}

package com.heady.headyback.member.dto.request;

import com.heady.headyback.member.exception.MemberErrorMessage;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MemberRegisterRequest(
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

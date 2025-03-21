package com.heady.headyback.auth.service;

import static com.heady.headyback.user.exception.MemberExceptionCode.*;

import org.springframework.stereotype.Service;

import com.heady.headyback.auth.dto.request.LoginRequest;
import com.heady.headyback.common.exception.CustomException;
import com.heady.headyback.user.domain.Email;
import com.heady.headyback.user.domain.Member;
import com.heady.headyback.user.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberRepository memberRepository;

	public void login(LoginRequest request) {
		Member member = memberRepository.findByEmail(Email.ofCreate(request.email()))
				.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
		checkPassword(member, request.password());
	}

	private void checkPassword(Member member, String rowPassword) {
		member.getPassword().matches(rowPassword);
	}
}

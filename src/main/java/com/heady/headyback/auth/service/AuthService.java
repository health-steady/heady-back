package com.heady.headyback.auth.service;

import static com.heady.headyback.member.exception.MemberExceptionCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heady.headyback.auth.domain.Accessor;
import com.heady.headyback.auth.dto.AuthTokenDto;
import com.heady.headyback.auth.dto.request.LoginRequest;
import com.heady.headyback.auth.jwt.JwtProvider;
import com.heady.headyback.common.exception.CustomException;
import com.heady.headyback.member.domain.Email;
import com.heady.headyback.member.domain.Member;
import com.heady.headyback.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberRepository memberRepository;
	private final JwtProvider jwtProvider;

	public AuthTokenDto login(LoginRequest request) {
		Member member = memberRepository.findByEmail(Email.ofCreate(request.email()))
				.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
		checkPassword(member, request.password());
		return createAuthToken(member);
	}

	@Transactional(readOnly = true)
	public Accessor getAuthMember(Long memberId) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
		return Accessor.member(member.getId());
	}

	private void checkPassword(Member member, String rowPassword) {
		member.getPassword().matches(rowPassword);
	}

	private AuthTokenDto createAuthToken(Member member) {
		return AuthTokenDto.of(
				jwtProvider.createAccessToken(member),
				jwtProvider.createRefreshToken(member)
		);
	}
}

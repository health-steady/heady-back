package com.heady.headyback.auth.service;

import static com.heady.headyback.member.exception.MemberExceptionCode.*;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heady.headyback.auth.domain.Accessor;
import com.heady.headyback.auth.dto.AuthTokenDto;
import com.heady.headyback.auth.dto.request.LoginRequest;
import com.heady.headyback.auth.dto.request.OauthLoginRequest;
import com.heady.headyback.auth.jwt.JwtProvider;
import com.heady.headyback.auth.oauth.OauthProvider;
import com.heady.headyback.auth.oauth.OauthProviders;
import com.heady.headyback.auth.oauth.userInfo.OauthUserInfo;
import com.heady.headyback.common.exception.CustomException;
import com.heady.headyback.member.domain.Email;
import com.heady.headyback.member.domain.Member;
import com.heady.headyback.member.domain.enumerated.SocialProvider;
import com.heady.headyback.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

	private final OauthProviders oauthProviders;
	private final MemberRepository memberRepository;
	private final JwtProvider jwtProvider;

	public AuthTokenDto oauthLogin(OauthLoginRequest request) {
		OauthProvider provider = oauthProviders.getProvider(request.socialProvider());
		String accessToken = provider.getAccessToken(request.code());
		OauthUserInfo userInfo = provider.getUserInfo(accessToken);
		return oauthLoginProcess(
				userInfo,
				request.socialProvider()
		);
	}

	public AuthTokenDto login(LoginRequest request) {
		Member member = memberRepository.findByEmail(Email.ofCreate(request.email()))
				.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
		checkPassword(member, request.password());
		return createAuthToken(member);
	}

	@Transactional(readOnly = true)
	public Accessor getAuthMember(UUID publicId) {
		Member member = memberRepository.findByPublicId(publicId)
				.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
		return Accessor.member(member.getPublicId());
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

	private AuthTokenDto oauthLoginProcess(
			OauthUserInfo userInfo,
			SocialProvider socialProvider
	) {
		Member member = memberRepository.findBySocialId(userInfo.getSocialId())
				.orElseGet(() -> memberRepository.save(
						Member.signUpWithOauth(
								userInfo.getEmail(),
								userInfo.getNickName(),
								socialProvider,
								userInfo.getSocialId()
						)
				));

		return createAuthToken(member);
	}
}

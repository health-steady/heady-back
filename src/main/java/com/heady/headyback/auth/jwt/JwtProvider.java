package com.heady.headyback.auth.jwt;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.heady.headyback.user.domain.Member;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtProvider {
	private final JwtProperties jwtProperties;

	public String createAccessToken(Member member) {
		long accessTokenExpirationMillis = jwtProperties.getAccessTokenExpirationMillis();
		return createToken(member, accessTokenExpirationMillis, TokenType.ACCESS_TOKEN);
	}

	public String createRefreshToken(Member member) {
		long refreshTokenExpirationMillis = jwtProperties.getRefreshTokenExpirationMillis();
		return createToken(member, refreshTokenExpirationMillis, TokenType.REFRESH_TOKEN);
	}

	private String createToken(Member member, long expirationMillis, TokenType tokenType) {
		Date now = new Date();
		Date expiredDate = new Date(now.getTime() + expirationMillis);

		return Jwts.builder()
				.setSubject(member.getId().toString())
				.setIssuedAt(now)
				.setExpiration(expiredDate)
				.claim(JwtProperties.TOKEN_TYPE, tokenType.name())
				.signWith(jwtProperties.getSecretKey())
				.compact();
	}
}

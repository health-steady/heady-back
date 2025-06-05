package com.heady.headyback.auth.jwt;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.heady.headyback.member.domain.Member;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtProvider {
	private final JwtProperties jwtProperties;

	public String createAccessToken(UUID publicId) {
		long accessTokenExpirationMillis = jwtProperties.getAccessTokenExpirationMillis();
		return createToken(publicId, accessTokenExpirationMillis, TokenType.ACCESS_TOKEN);
	}

	public String createRefreshToken(UUID publicId) {
		long refreshTokenExpirationMillis = jwtProperties.getRefreshTokenExpirationMillis();
		return createToken(publicId, refreshTokenExpirationMillis, TokenType.REFRESH_TOKEN);
	}

	private String createToken(UUID publicId, long expirationMillis, TokenType tokenType) {
		Date now = new Date();
		Date expiredDate = new Date(now.getTime() + expirationMillis);

		return Jwts.builder()
				.setSubject(publicId.toString())
				.setIssuedAt(now)
				.setExpiration(expiredDate)
				.claim(JwtProperties.TOKEN_TYPE, tokenType.name())
				.signWith(jwtProperties.getSecretKey())
				.compact();
	}
}

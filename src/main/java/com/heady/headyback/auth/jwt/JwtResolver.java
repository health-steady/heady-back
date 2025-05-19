package com.heady.headyback.auth.jwt;

import static com.heady.headyback.auth.exception.AuthExceptionCode.*;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.heady.headyback.common.exception.CustomException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtResolver {

	private final JwtProperties jwtProperties;

	public boolean validate(String token) {
		try {
			Jwts.parserBuilder()
					.setSigningKey(jwtProperties.getSecretKey())
					.build()
					.parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException e) {
			throw new CustomException(AUTH_TOKEN_EXPIRED);
		} catch (JwtException e) {
			throw new CustomException(AUTH_TOKEN_INVALID);
		}
	}

	public UUID getMemberPublicId(String token) {
		return UUID.fromString(parseClaims(token).getSubject());
	}

	private Claims parseClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(jwtProperties.getSecretKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
}

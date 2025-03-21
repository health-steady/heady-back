package com.heady.headyback.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heady.headyback.auth.dto.AuthTokenDto;
import com.heady.headyback.auth.dto.request.LoginRequest;
import com.heady.headyback.auth.service.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/v1")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		AuthTokenDto token = authService.login(request);
		log.info("accessToken: {}", token.accessToken());
		log.info("refreshToken: {}", token.refreshToken());
		return ResponseEntity.ok(null);
	}
}

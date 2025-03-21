package com.heady.headyback.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heady.headyback.auth.dto.request.LoginRequest;
import com.heady.headyback.auth.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/v1")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		authService.login(request);
		return ResponseEntity.ok(null);
	}
}

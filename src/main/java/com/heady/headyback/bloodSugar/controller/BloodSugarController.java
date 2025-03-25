package com.heady.headyback.bloodSugar.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heady.headyback.auth.annotation.Auth;
import com.heady.headyback.auth.domain.Accessor;
import com.heady.headyback.bloodSugar.dto.request.RecordRequest;
import com.heady.headyback.bloodSugar.dto.response.RecordResponse;
import com.heady.headyback.bloodSugar.service.BloodSugarService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bloodSugars/v1")
public class BloodSugarController {

	private final BloodSugarService bloodSugarService;

	@PostMapping
	public ResponseEntity<RecordResponse> record(
			@Auth Accessor accessor,
			@RequestBody RecordRequest request
	) {

		return ResponseEntity.ok().body(
				RecordResponse.of(
						bloodSugarService.record(accessor, request)
				)
		);
	}
}

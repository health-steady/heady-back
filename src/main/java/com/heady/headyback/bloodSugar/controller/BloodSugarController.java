package com.heady.headyback.bloodSugar.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.heady.headyback.auth.annotation.Auth;
import com.heady.headyback.auth.domain.Accessor;
import com.heady.headyback.bloodSugar.dto.request.BloodSugarRequest;
import com.heady.headyback.bloodSugar.dto.response.BloodSugarResponse;
import com.heady.headyback.bloodSugar.service.BloodSugarService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bloodSugars/v1")
public class BloodSugarController {

	private final BloodSugarService bloodSugarService;

	@PostMapping
	public ResponseEntity<BloodSugarResponse> record(
			@Auth Accessor accessor,
			@RequestBody @Valid BloodSugarRequest request
	) {

		return ResponseEntity.ok().body(
				BloodSugarResponse.of(
						bloodSugarService.record(accessor, request)
				)
		);
	}

	@GetMapping
	public ResponseEntity<List<BloodSugarResponse>> getAllByDate(
			@Auth Accessor accessor,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
	) {

		return ResponseEntity.ok().body(
				bloodSugarService.getAllByDate(accessor, date)
						.stream()
						.map(BloodSugarResponse::of)
						.collect(Collectors.toList())
		);
	}
}

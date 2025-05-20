package com.heady.headyback.ai.service;

import org.springframework.stereotype.Service;

import com.heady.headyback.bloodSugar.repository.BloodSugarRepository;
import com.heady.headyback.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AiAnalysisService {

	private final MemberRepository memberRepository;
	private final BloodSugarRepository bloodSugarRepository;

	public void analyzeHealth() {
	}
}

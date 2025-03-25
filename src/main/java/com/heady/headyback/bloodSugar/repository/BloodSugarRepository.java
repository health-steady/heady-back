package com.heady.headyback.bloodSugar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.heady.headyback.bloodSugar.domain.BloodSugar;

@Repository
public interface BloodSugarRepository extends JpaRepository<BloodSugar, Long> {
}

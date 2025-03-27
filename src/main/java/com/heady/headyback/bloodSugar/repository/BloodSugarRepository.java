package com.heady.headyback.bloodSugar.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.heady.headyback.bloodSugar.domain.BloodSugar;

@Repository
public interface BloodSugarRepository extends JpaRepository<BloodSugar, Long> {
	@Query("""
			    SELECT bs FROM BloodSugar bs
			    LEFT JOIN FETCH bs.meal m
			    LEFT JOIN FETCH m.foods
			    WHERE bs.member.id = :memberId
			      AND bs.measuredAt BETWEEN :start AND :end
			    ORDER BY bs.measuredAt
			""")
	List<BloodSugar> findAllByMemberIdAndMeasuredAtBetween(
			@Param("memberId") Long memberId,
			@Param("start") LocalDateTime start,
			@Param("end") LocalDateTime end
	);
}

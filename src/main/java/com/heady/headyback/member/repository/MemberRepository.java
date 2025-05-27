package com.heady.headyback.member.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.heady.headyback.member.domain.Email;
import com.heady.headyback.member.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	@Query("SELECT EXISTS (SELECT 1 FROM Member m WHERE m.email = :email)")
	boolean existsByEmail(@Param("email") Email email);

	@Query("SELECT m FROM Member m where m.email = :email and m.isDeleted = false")
	Optional<Member> findByEmail(@Param("email") Email email);

	@Query("""
			select m from Member m
			WHERE m.socialId = :socialId
			""")
	Optional<Member> findBySocialId(@Param("socialId") String socialId);

	Optional<Member> findByPublicId(UUID publicId);
}

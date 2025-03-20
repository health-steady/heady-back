package com.heady.headyback.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.heady.headyback.user.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	@Query("SELECT EXISTS (SELECT 1 FROM Member m WHERE m.email.email = :email)")
	boolean existsByEmail(@Param("email") String email);
}

package com.heady.headyback.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.heady.headyback.user.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}

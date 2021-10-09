package com.manduljo.ohou.repository.member;

import com.manduljo.ohou.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member>findByEmail(String email);
}

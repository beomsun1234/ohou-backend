package com.manduljo.ohou.repository.member;

import com.manduljo.ohou.domain.member.Member;
import com.manduljo.ohou.domain.member.StatusAt;
import org.springframework.data.jpa.repository.JpaRepository;



import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member>findByEmail(String email);

    List<Member> findByStatusAt(StatusAt statusAt);

    Optional<Member>findByEmailAndStatusAt(String email,StatusAt statusAt);

}

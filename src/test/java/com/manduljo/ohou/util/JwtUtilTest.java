package com.manduljo.ohou.util;

import com.manduljo.ohou.domain.member.LoginType;
import com.manduljo.ohou.domain.member.Member;
import com.manduljo.ohou.domain.member.Role;
import com.manduljo.ohou.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class JwtUtilTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @Transactional
    @Rollback
    @BeforeEach
    void member생성및저장(){
        memberRepository.save(Member.builder().name("test").role(Role.ROLE_USER).email("test").loginType(LoginType.OHOU).build());
    }
    
    @Test
    @DisplayName("해당 유저 토큰생성 및 해당유저 email과 token에 email이 같은지 확인")
    void test01(){

        //when
        Member member = memberRepository.findById(1L).orElseThrow();
        String token = jwtUtil.generateToken(member.getEmail(), member.getName());

        //then
        Assertions.assertThat(member.getEmail()).isEqualTo(jwtUtil.getEmail(token));
    }

    @Test
    @DisplayName("토큰 유효성 검사")
    void test2(){
        //when
        Member member = memberRepository.findById(1L).orElseThrow();
        String token = jwtUtil.generateToken(member.getEmail(), member.getName());
        //then
        Assertions.assertThat(jwtUtil.validateToken(token, member)).isTrue();
    }




}
package com.manduljo.ohou.util;


import com.manduljo.ohou.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class JwtUtilTest {



    private JwtUtil jwtUtil;

    @BeforeEach
    void init(){
        jwtUtil = new JwtUtil("testtestsetsettestsetetestestestestestsettsetse");
    }


    
    @Test
    @DisplayName("토큰생성 및 유저 email과 token에 email이 같은지 확인")
    void test_generateToken(){
        //given
        Member member = Member.builder().email("test").name("test").build();
        String token = jwtUtil.generateToken(member.getEmail(), member.getName());
        //when
        String email = jwtUtil.getEmail(token);
        //then
        Assertions.assertThat(email).isEqualTo(member.getEmail());
        System.out.println("token="+token);

    }

    @Test
    @DisplayName("토큰 유효성 검사")
    void test2(){
        //given
        Member member = Member.builder().email("test").name("test").build();
        String token = jwtUtil.generateToken(member.getEmail(), member.getName());
        //when
        Boolean validateToken = jwtUtil.validateToken(token, member);
        //then
        Assertions.assertThat(validateToken).isTrue() ;
    }




}
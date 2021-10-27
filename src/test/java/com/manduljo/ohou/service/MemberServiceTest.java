package com.manduljo.ohou.service;

import com.manduljo.ohou.ApiCommonResponse;
import com.manduljo.ohou.domain.member.Gender;
import com.manduljo.ohou.domain.member.Member;
import com.manduljo.ohou.domain.member.dto.MemberInfo;
import com.manduljo.ohou.domain.member.dto.MemberJoinRequestDto;
import com.manduljo.ohou.domain.member.dto.MemberUpdateInfoRequestDto;
import com.manduljo.ohou.domain.member.dto.MemberUpdatePasswordDto;
import com.manduljo.ohou.repository.member.MemberRepository;
import com.manduljo.ohou.util.ImageUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.io.IOException;
import java.util.Objects;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("회원가입 테스트")
    void test_join(){
        //given
        MemberJoinRequestDto memberJoinRequestDto = MemberJoinRequestDto.builder().nickname("test").password("1234").email("test").build();
        Member member = memberJoinRequestDto.toEntity(bCryptPasswordEncoder.encode(memberJoinRequestDto.getPassword()));
        given(memberRepository.save(any(Member.class))).willReturn(member);
        //when
        ApiCommonResponse join = memberService.join(memberJoinRequestDto);
        //then
        Assertions.assertThat(join.getStatus()).isEqualTo("200");
    }

    @Test
    @DisplayName("유저 id 조회")
    void test_getUserInfoById(){
        //given
        Long memberFakeId = 1L;
        Member member = Member.builder().id(memberFakeId).name("test").email("test").build();
        given(memberRepository.findById(memberFakeId)).willReturn(java.util.Optional.ofNullable(member));
        //when
        MemberInfo memberInfo = memberService.getUserInfoById(memberFakeId);
        //then
        Assertions.assertThat(memberInfo.getEmail()).isEqualTo("test");
    }

    @Test
    @DisplayName("유저정보 변경 및 프로필 업로드")
    void test_updateInfo() throws IOException {
        //given
        Long memberFakeId = 1L;
        Member member = Member.builder().id(memberFakeId).name("test").email("test").build();
        MemberUpdateInfoRequestDto memberUpdateDto = MemberUpdateInfoRequestDto.builder().gender(Gender.MAN).nickname("update").build();
        given(memberRepository.findById(memberFakeId)).willReturn(java.util.Optional.ofNullable(member));
        //when
        when(memberRepository.save(Objects.requireNonNull(member).updateMyInfo(memberUpdateDto.getNickname(),memberUpdateDto.getGender(),null,memberUpdateDto.getIntroduce())))
                .thenReturn(member);
        MemberInfo memberInfo = memberService.updateInfo(memberFakeId, memberUpdateDto);
        //then
        Assertions.assertThat(memberInfo.getNickname()).isEqualTo("update");
    }

    @Test
    @DisplayName("유저 패스워드 변경")
    void test_updatePassword(){
        //given
        Long memberFakeId = 1L;
        Member member = Member.builder().id(memberFakeId).name("test").email("test").password(bCryptPasswordEncoder.encode("1234")).build();
        MemberUpdatePasswordDto passwordDto = MemberUpdatePasswordDto.builder().checkPassword("12345").password("12345").build();
        String updatePassword = bCryptPasswordEncoder.encode(passwordDto.getPassword());
        given(memberRepository.findById(memberFakeId)).willReturn(java.util.Optional.ofNullable(member));
        //when
        when(memberRepository.save(Objects.requireNonNull(member).updatePassword(updatePassword))).thenReturn(member);
        ApiCommonResponse password = memberService.updatePassword(memberFakeId, passwordDto);
        //then
        Assertions.assertThat(password.getData()).isEqualTo(1L);
    }




}
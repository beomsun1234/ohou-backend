package com.manduljo.ohou.controller;

import com.manduljo.ohou.ApiComonResponse;
import com.manduljo.ohou.domain.member.dto.MemberJoinRequestDto;
import com.manduljo.ohou.domain.member.dto.MemberUpdateInfoRequestDto;
import com.manduljo.ohou.domain.member.dto.MemberUpdatePasswordDto;
import com.manduljo.ohou.repository.member.MemberRepository;
import com.manduljo.ohou.service.MemberService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    /**
     * 로그인
     * @param memberJoinRequestDto
     * @return
     */
    @PostMapping("/join")
    public ApiComonResponse join(@RequestBody MemberJoinRequestDto memberJoinRequestDto){
        return memberService.join(memberJoinRequestDto);
    }

    /**
     * 회원정보확인
     * @param authentication
     * @param userId
     * @return
     */
    @GetMapping("/api/member/{id}")
    public ApiComonResponse getInfo(Authentication authentication,@PathVariable(name = "id") Long userId){
        return memberService.getUserInfo(authentication, userId);
    }

    /**
     * 회원정보수정
     * updateMyInfo(String name, Gender gender, String profileImage,String introduce)
     * @param authentication
     * @param userId
     * @param memberUpdateRequestDto
     * @return
     */
    @PutMapping("/api/member/{id}")
    public Long updateMemberInfo(Authentication authentication, @PathVariable(name = "id") Long userId, MemberUpdateInfoRequestDto memberUpdateRequestDto) throws IOException {
        return memberService.updateInfo(authentication, userId,memberUpdateRequestDto);
    }

    /**
     * 비밀번호 변경
     * @param authentication
     * @param userId
     * @param memberUpdatePasswordDto
     * @return
     */
    @PutMapping("/api/member/{id}/password")
    public ApiComonResponse updatePassword(Authentication authentication, @PathVariable(name = "id") Long userId, @RequestBody MemberUpdatePasswordDto memberUpdatePasswordDto){
        return memberService.updatePassword(authentication, userId,memberUpdatePasswordDto);
    }



}

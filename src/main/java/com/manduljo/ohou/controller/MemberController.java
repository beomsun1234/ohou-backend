package com.manduljo.ohou.controller;

import com.manduljo.ohou.domain.member.Member;
import com.manduljo.ohou.domain.member.dto.MemberJoinRequestDto;
import com.manduljo.ohou.domain.member.dto.MemberUpdateRequestDto;
import com.manduljo.ohou.oauth2.CustomUserDetails;
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
    @PostMapping("/join")
    public Long join(@RequestBody MemberJoinRequestDto memberJoinRequestDto){
        return memberService.join(memberJoinRequestDto);
    }

    /**
     * updateMyInfo(String name, Gender gender, String profileImage,String introduce)
     * @param authentication
     * @param userId
     * @param memberUpdateRequestDto
     * @return
     */
    @PutMapping("/api/v1/member/{id}")
    public Long update(Authentication authentication, @PathVariable(name = "id") Long userId, MemberUpdateRequestDto memberUpdateRequestDto) throws IOException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        if (!userDetails.getMember().getId().equals(userId)){
            throw new IllegalArgumentException("잘못된 사용자입니다.");
        }
        return memberService.updateInfo(userId,memberUpdateRequestDto);
    }
}

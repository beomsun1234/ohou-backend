package com.manduljo.ohou.controller;

import com.manduljo.ohou.domain.member.dto.MemberJoinRequestDto;
import com.manduljo.ohou.service.MemberService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/join")
    public Long join(@RequestBody MemberJoinRequestDto memberJoinRequestDto){
        return memberService.join(memberJoinRequestDto);
    }
}

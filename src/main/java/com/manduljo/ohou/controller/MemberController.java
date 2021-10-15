package com.manduljo.ohou.controller;

import com.manduljo.ohou.ApiCommonResponse;
import com.manduljo.ohou.domain.member.dto.MemberInfo;
import com.manduljo.ohou.domain.member.dto.MemberJoinRequestDto;
import com.manduljo.ohou.domain.member.dto.MemberUpdateInfoRequestDto;
import com.manduljo.ohou.domain.member.dto.MemberUpdatePasswordDto;
import com.manduljo.ohou.service.MemberService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Time;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class MemberController {
    private final MemberService memberService;
    /**
     * 회원가입
     * @param memberJoinRequestDto
     * @return
     */
    @PostMapping("join")
    public ResponseEntity<ApiCommonResponse> join(@RequestBody MemberJoinRequestDto memberJoinRequestDto){
        return new ResponseEntity<>(memberService.join(memberJoinRequestDto), HttpStatus.CREATED);
    }

    /**
     * 회원정보확인
     * @param userId
     * @return
     */
    @GetMapping("member/{id}")
    public ResponseEntity<ApiCommonResponse> getInfo(@PathVariable(name = "id") Long userId){
        return new ResponseEntity<>(ApiCommonResponse
                .builder()
                .message("조회성공")
                .status(String.valueOf(HttpStatus.OK.value()))
                .data(memberService.getUserInfoById(userId))
                .build(),HttpStatus.OK);
    }

    /**
     * 회원정보수정
     * updateMyInfo(String name, Gender gender, String profileImage,String introduce)
     * @param userId
     * @param memberUpdateRequestDto
     * @return
     */
    @PutMapping("member/{id}")
    public ResponseEntity<ApiCommonResponse> updateMemberInfo(@PathVariable(name = "id") Long userId, MemberUpdateInfoRequestDto memberUpdateRequestDto) throws IOException {
        return new ResponseEntity<>(ApiCommonResponse
                .builder()
                .message("정보변경 성공")
                .status(String.valueOf(HttpStatus.OK.value()))
                .data(memberService.updateInfo(userId,memberUpdateRequestDto))
                .build(), HttpStatus.OK);
    }
    /**
     * 비밀번호 변경
     * @param userId
     * @param memberUpdatePasswordDto
     * @return
     */
    @PutMapping("member/{id}/password")
    public ResponseEntity<ApiCommonResponse> updatePassword(@PathVariable(name = "id") Long userId, @RequestBody MemberUpdatePasswordDto memberUpdatePasswordDto){
        return new ResponseEntity<>(memberService.updatePassword(userId,memberUpdatePasswordDto),HttpStatus.OK);
    }


}

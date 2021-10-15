package com.manduljo.ohou.controller;

import com.manduljo.ohou.ApiCommonResponse;
import com.manduljo.ohou.domain.member.dto.MemberLoginRequestDto;
import com.manduljo.ohou.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class AuthController {
    private final AuthService authService;

    @PostMapping("auth/login")
    public ResponseEntity<ApiCommonResponse> authLogin(@RequestBody MemberLoginRequestDto memberLoginRequestDto){
        return new ResponseEntity<>(authService.login(memberLoginRequestDto), HttpStatus.OK);
    }

}

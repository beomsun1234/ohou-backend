package com.manduljo.ohou.service;

import com.manduljo.ohou.ApiCommonResponse;
import com.manduljo.ohou.domain.member.dto.MemberLoginRequestDto;
import com.manduljo.ohou.oauth2.CustomUserDetails;
import com.manduljo.ohou.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtils;

    public ApiCommonResponse login(MemberLoginRequestDto memberLoginRequestDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(memberLoginRequestDto.getEmail(), memberLoginRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return ApiCommonResponse.builder()
                .message("로그인성공")
                .status(String.valueOf(HttpStatus.OK.value()))
                .data(jwtUtils.generateToken(userDetails.getMember().getEmail(), userDetails.getMember().getName()))
                .build();
    }

}

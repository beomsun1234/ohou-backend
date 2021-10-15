package com.manduljo.ohou.filter;

import com.manduljo.ohou.domain.member.Member;
import com.manduljo.ohou.oauth2.CustomUserDetails;
import com.manduljo.ohou.repository.member.MemberRepository;
import com.manduljo.ohou.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException{
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }

        String token = jwtUtil.extractHeader(authHeader);

        log.info("token={}", token);

        SecurityContextHolder.getContext().setAuthentication(getUsernamePasswordAuthentication(request,response, token));
        log.info("-------------인가------------------------------------");
        filterChain.doFilter(request,response);
    }

    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthentication(HttpServletRequest request,HttpServletResponse response, String token) throws Exception {
        log.info("-------------토큰 확인중--------------------------");
        try {
            String email = jwtUtil.getEmail(token);
            Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("없는 사용자 입니다."));
            if (jwtUtil.validateToken(token, member)) {
                log.info("email={}", member.getEmail());
                CustomUserDetails customUserDetail = new CustomUserDetails(member);
                return new UsernamePasswordAuthenticationToken(
                        customUserDetail, null, customUserDetail.getAuthorities());
            }
        }
        catch (ExpiredJwtException ex) {
            request.setAttribute("exception", ex.getMessage());
        }
        return null;
    }

}

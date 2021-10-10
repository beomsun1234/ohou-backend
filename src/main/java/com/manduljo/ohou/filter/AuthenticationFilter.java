package com.manduljo.ohou.filter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manduljo.ohou.domain.member.Member;
import com.manduljo.ohou.oauth2.CustomUserDetails;
import com.manduljo.ohou.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil){
        this.authenticationManager = authenticationManager;
        this.jwtUtil =  jwtUtil;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
            Member loginUser = new ObjectMapper().readValue(request.getInputStream(), Member.class);
            log.info("email={}", loginUser.getEmail());
            log.info("pass={}", loginUser.getPassword());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword());
            //  CustomUserDetailsService의 loadByUsername() 함수가 실행된다(나는 email로 호출한다)
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            // authentication 객체가 세션영역에 저장됨 => 로그인이 되었다는 뜻
            //SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
            log.info("-----------로그인완료됨");
            //log.info("email={}", securityUser.getEmail());
            return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("로그인성공");
        CustomUserDetails userDetails = (CustomUserDetails) authResult.getPrincipal();
        log.info("usserDetail={}",userDetails);
        String token = jwtUtil.generateToken(userDetails.getMember().getEmail(), userDetails.getMember().getName());
        PrintWriter printWriter = response.getWriter();
        printWriter.write("token="+token);
        printWriter.flush();
    }

}

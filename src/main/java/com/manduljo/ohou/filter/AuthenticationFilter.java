package com.manduljo.ohou.filter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manduljo.ohou.domain.member.Member;
import com.manduljo.ohou.oauth2.CustomUserDetails;
import com.manduljo.ohou.util.CookieUtil;
import com.manduljo.ohou.util.JwtUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    public AuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil, CookieUtil cookieUtil){
        this.authenticationManager = authenticationManager;
        this.jwtUtil =  jwtUtil;
        this.cookieUtil = cookieUtil;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
            Member loginUser = new ObjectMapper().readValue(request.getInputStream(), Member.class);
            log.info("email={}", loginUser.getEmail());
            log.info("pass={}", loginUser.getPassword());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            log.info("-----------로그인완료됨");
            return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("로그인성공");
        CustomUserDetails userDetails = (CustomUserDetails) authResult.getPrincipal();
        log.info("usserDetail={}",userDetails);
        String token = jwtUtil.generateToken(userDetails.getMember().getEmail(), userDetails.getMember().getName());
        log.info("token={}", token);
        //쿠키 or 쿼리스티링
        response.addCookie(cookieUtil.generateCookie("token",token));
        response.sendRedirect("http://localhost:8080/?auth=success");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("실패");
        response.sendRedirect("http://localhost:3000/?state=fail");
    }
}

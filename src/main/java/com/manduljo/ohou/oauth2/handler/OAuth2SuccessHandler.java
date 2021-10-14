package com.manduljo.ohou.oauth2.handler;

import com.manduljo.ohou.oauth2.OAuth2Attributes;
import com.manduljo.ohou.util.CookieUtil;
import com.manduljo.ohou.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Component
@Slf4j
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        OAuth2Attributes userInfo = getUserInfo(oAuth2User);

        String token = jwtUtil.generateToken(userInfo.getEmail(), userInfo.getName());
        log.info("token={}", token);
        //쿠키 or 쿼리스티링 정하기
        response.addCookie(cookieUtil.generateCookie("token", token));
        response.sendRedirect("http://localhost:3000/?state=success");
    }


    //naver(UsernameAttribute) - response, kakao = id
    private OAuth2Attributes getUserInfo(OAuth2User oAuth2User){
        Map<String,Object> data = oAuth2User.getAttributes();
        if(data.get("response") != null){
            return OAuth2Attributes.of("naver","response",data);
        }
        if(data.get("kakao_account")!=null){
            return OAuth2Attributes.of("kakao","id",data);
        }
        return OAuth2Attributes.of("google","google",data);
    }
}

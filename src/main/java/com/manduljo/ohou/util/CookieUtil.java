package com.manduljo.ohou.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;

@Component
public class CookieUtil {

    public Cookie generateCookie(String key, String value){
        Cookie cookie = new Cookie(key,value);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) JwtUtil.TOKEN_VALIDATION_SECOND);
        cookie.setPath("/");
        return cookie;
    }
}

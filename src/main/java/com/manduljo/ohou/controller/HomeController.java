package com.manduljo.ohou.controller;



import com.manduljo.ohou.oauth2.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class HomeController {
    @GetMapping("/")
    public String home(){
        return "hello";
    }

    @GetMapping("/api/test")
    public String test(Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        log.info("name={}",userDetails.getMember().getName());
        return "hello "+userDetails.getMember().getName();
    }

}

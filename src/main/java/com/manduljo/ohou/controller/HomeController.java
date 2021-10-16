package com.manduljo.ohou.controller;



import com.manduljo.ohou.oauth2.CustomUserDetails;
import com.manduljo.ohou.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    private final ProductService productService;
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

    @GetMapping("/test")
    public String test2(){
        productService.createProduct();
        return "gd";
    }

}

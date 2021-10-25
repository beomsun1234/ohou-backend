package com.manduljo.ohou.controller;



import com.manduljo.ohou.oauth2.CustomUserDetails;
import com.manduljo.ohou.service.ProductService;
import com.manduljo.ohou.util.IpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    private final ProductService productService;
    private final IpUtil ipUtil;
    @GetMapping("/")
    public String home(){
        return "hello";
    }


    @GetMapping("/ip")
    public String ip(HttpServletRequest httpServletRequest) throws Exception {
        return ipUtil.getUserIp(httpServletRequest);
    }

    @GetMapping("/api/test")
    public String test(Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        log.info("name={}",userDetails.getMember().getName());
        return "hello "+userDetails.getMember().getName();
    }

    @CacheEvict(value = "products", allEntries = true)
    @PostMapping("/test")
    public String test2(@RequestParam String id){
        log.info("id={}",id);
        productService.createProduct(id);
        return "gd";
    }

}

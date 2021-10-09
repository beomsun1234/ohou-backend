package com.manduljo.ohou;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String home(){
        return "hello ohou_frontend?";
    }

    @GetMapping("/api/test")
    public String test(){
        return "success";
    }
}

package com.manduljo.ohou.controller;

import com.manduljo.ohou.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("test")
public class TestController {
    private final OrderService orderService;
    @GetMapping("order")
    public String test(@RequestParam(defaultValue = "0",name = "check") int ck){
        orderService.createOrder(ck);
        return "성공";
    }
}

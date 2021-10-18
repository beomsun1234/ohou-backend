package com.manduljo.ohou.controller;

import com.manduljo.ohou.domain.cart.dto.CartInfo;
import com.manduljo.ohou.service.CartService;
import com.manduljo.ohou.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("test")
public class TestController {
    private final OrderService orderService;
    private final CartService cartService;


    @GetMapping("order")
    public String test(@RequestParam(defaultValue = "0",name = "check") int ck){
        log.info("ck={}",ck);
        orderService.createOrder(ck);
        return "성공";
    }

    @PostMapping("cart/{member_id}")
    public Long test2(@PathVariable(name = "member_id") Long id){
        return cartService.createAndAddCart(id);
    }

    @GetMapping("cart/{member_id}")
    public Map<String,Object> getCartInfo(@PathVariable(name = "member_id") Long id){
        Map<String, Object> map = new HashMap<>();
        List<CartInfo> cartInfos = cartService.findCartsByMemberId(id);
        map.put("cartItem",cartInfos);
        map.put("totalPrice", cartInfos.stream().mapToInt(CartInfo::getPrice).sum());
        return map;
    }

}

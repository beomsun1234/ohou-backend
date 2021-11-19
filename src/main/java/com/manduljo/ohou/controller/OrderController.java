package com.manduljo.ohou.controller;

import com.manduljo.ohou.domain.orders.dto.CartOrderDto;
import com.manduljo.ohou.domain.orders.dto.OrderDto;
import com.manduljo.ohou.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("members/{id}/orders")
    public String createOrder(@PathVariable Long id, @RequestBody OrderDto orderDto){
        log.info("ids={}",orderDto.getProductId().get(0));
        orderService.createOrder(id,orderDto);
        return "주문성공";
    }

    @PostMapping("members/{id}/cart/orders")
    public String createCartOrder(@PathVariable Long id, @RequestBody CartOrderDto cartOrderDto){
        orderService.createCartOrder(id,cartOrderDto);
        return "주문성공";
    }

}

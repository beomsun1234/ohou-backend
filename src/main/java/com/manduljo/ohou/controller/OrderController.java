package com.manduljo.ohou.controller;

import com.manduljo.ohou.ApiCommonResponse;
import com.manduljo.ohou.domain.orders.dto.CartOrderDto;
import com.manduljo.ohou.domain.orders.dto.OrderDto;
import com.manduljo.ohou.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("members/{id}/orders")
    public ApiCommonResponse createOrder(@PathVariable Long id, @RequestBody OrderDto orderDto){
        log.info("ids={}",orderDto.getProductId().get(0));
        orderService.createOrder(id,orderDto);
        return ApiCommonResponse.builder()
                .data("주문 성공")
                .message("주문 성공")
                .status(String.valueOf(HttpStatus.OK))
                .build();
    }

    @PostMapping("members/{id}/cart/orders")
    public ApiCommonResponse createCartOrder(@PathVariable Long id, @RequestBody CartOrderDto cartOrderDto){
        orderService.createCartOrder(id,cartOrderDto);
        return ApiCommonResponse.builder()
                .data("주문 성공")
                .message("장바구니 주문 성공")
                .status(String.valueOf(HttpStatus.OK))
                .build();
    }

}

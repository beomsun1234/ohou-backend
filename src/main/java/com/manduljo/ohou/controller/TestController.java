package com.manduljo.ohou.controller;

import com.manduljo.ohou.domain.cart.dto.CartIItemInfo;
import com.manduljo.ohou.domain.cart.dto.CartInfo;
import com.manduljo.ohou.domain.cart.dto.CartItemAddDto;
import com.manduljo.ohou.domain.cartItem.dto.CartItemUpdateQuantityDto;
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

    /**
     * 카트 생성 및 추가 및 업데이트
     * @param cartItemAddDto -> memberId, productId, quantity
     * @return -> 카트 아이디 반환
     */
    @PostMapping("cart")
    public Long createOrAddOrUpdateCart(@RequestBody CartItemAddDto cartItemAddDto){
        return cartService.createOrAddOrUpdateCart(cartItemAddDto);
    }

    /**
     * 카트조회
     * @param id-> 맴버 id 어차피 시큐리티 적용되면 id 받을 필요 없음! 아니면 member/id/cart 로 검색 할지 결정해야함
     * @return
     */
    @GetMapping("cart/{member_id}")
    public CartInfo getCartInfo(@PathVariable(name = "member_id") Long id){
        return cartService.findCartsByMemberId(id);
    }

    /**
     * 장바구니 안에서 수량 설정
     * @param cartItemUpdateQuantityDto = cartItemId, quantity
     * @return
     */
    @PutMapping("cart")
    public Long updateCartItemQuantity(@RequestBody CartItemUpdateQuantityDto cartItemUpdateQuantityDto){
        return cartService.updateCartItem(cartItemUpdateQuantityDto);
    }

}

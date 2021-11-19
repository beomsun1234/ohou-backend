package com.manduljo.ohou.controller;

import com.manduljo.ohou.domain.cart.dto.CartInfo;
import com.manduljo.ohou.domain.cart.dto.CartItemAddDto;
import com.manduljo.ohou.domain.cartItem.dto.CartItemUpdateQuantityDto;
import com.manduljo.ohou.service.CartService;
import com.manduljo.ohou.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("api")
public class CartController {
    private final CartService cartService;

    /**
     * 카트 생성 및 추가 및 업데이트
     * @param id -> memberId
     * @param cartItemAddDto -> productId, quantity
     * @return -> 카트 아이디 반환
     */
    @PostMapping("members/{id}/cart")
    public Long createOrAddOrUpdateCart(@PathVariable Long id,@RequestBody CartItemAddDto cartItemAddDto){
        return cartService.createOrAddOrUpdateCart(id, cartItemAddDto);
    }

    /**
     * 카트조회
     * @param id-> memberId
     * @return
     */
    @GetMapping("members/{id}/cart")
    public CartInfo getCartInfo(@PathVariable Long id){
        return cartService.findCartsByMemberId(id);
    }

    /**
     * 장바구니 안에서 수량 설정
     * @param id - memberId
     * @param cartItemUpdateQuantityDto = cartItemId, quantity
     * @return
     */
    @PutMapping("members/{id}/cart")
    public Long updateCartItemQuantity(@PathVariable Long id, @RequestBody CartItemUpdateQuantityDto cartItemUpdateQuantityDto){
        return cartService.updateCartItem(id,cartItemUpdateQuantityDto);
    }
}

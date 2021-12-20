package com.manduljo.ohou.controller;

import com.manduljo.ohou.ApiCommonResponse;
import com.manduljo.ohou.domain.cart.dto.CartInfo;
import com.manduljo.ohou.domain.cart.dto.CartItemAddDto;
import com.manduljo.ohou.domain.cartItem.dto.CartItemUpdateQuantityDto;
import com.manduljo.ohou.service.CartService;
import com.manduljo.ohou.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ApiCommonResponse createOrAddOrUpdateCart(@PathVariable Long id,@RequestBody CartItemAddDto cartItemAddDto){
        return ApiCommonResponse.builder()
                .status(String.valueOf(HttpStatus.OK))
                .data(cartService.createOrAddOrUpdateCart(id, cartItemAddDto))
                .message("카트 추가 및 업데이트 성공")
                .build();
    }

    /**
     * 카트조회
     * @param id-> memberId
     * @return
     */
    @GetMapping("members/{id}/cart")
    public ApiCommonResponse getCartInfo(@PathVariable Long id){
        return ApiCommonResponse.builder()
                .status(String.valueOf(HttpStatus.OK))
                .message("조히 성공")
                .data(cartService.findCartsByMemberId(id))
                .build();
    }

    /**
     * 장바구니 안에서 수량 설정
     * @param id - memberId
     * @param cartItemUpdateQuantityDto = cartItemId, quantity
     * @return
     */
    @PutMapping("members/{id}/cart")
    public ApiCommonResponse updateCartItemQuantity(@PathVariable Long id, @RequestBody CartItemUpdateQuantityDto cartItemUpdateQuantityDto){
        return ApiCommonResponse.builder()
                .status(String.valueOf(HttpStatus.OK))
                .data(cartService.updateCartItem(id,cartItemUpdateQuantityDto))
                .message("수량 변경 완료")
                .build();
    }

    /**
     * 카트 삭제
     */
    @DeleteMapping("members/{id}/cart")
    public ApiCommonResponse deleteCartItems(@PathVariable Long id,@RequestBody List<Long> cartItemIds){
        cartService.deleteCartItemByIdIn(cartItemIds);
        return ApiCommonResponse.builder()
                .status(String.valueOf(HttpStatus.OK))
                .data("카트아이템 삭제 완료")
                .message("카트아이템 삭제 완료")
                .build();

    }
}

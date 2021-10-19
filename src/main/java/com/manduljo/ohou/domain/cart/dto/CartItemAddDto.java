package com.manduljo.ohou.domain.cart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartItemAddDto {
    private Long memberId;//맴버아이디만 장바구니를 가지므로 필요함
    private Long productId;
    private int quantity;
}

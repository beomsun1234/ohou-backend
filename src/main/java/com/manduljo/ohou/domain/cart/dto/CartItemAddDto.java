package com.manduljo.ohou.domain.cart.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartItemAddDto {
    private Long productId;
    private int quantity;

    @Builder
    public CartItemAddDto(Long productId, int quantity){
        this.productId = productId;
        this.quantity = quantity;
    }
}

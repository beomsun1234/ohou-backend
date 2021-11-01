package com.manduljo.ohou.domain.cartItem.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartItemUpdateQuantityDto {
    private Long cartItemId;
    private int quantity;

    @Builder
    public CartItemUpdateQuantityDto(Long cartItemId, int quantity){
        this.cartItemId = cartItemId;
        this.quantity = quantity;
    }
}

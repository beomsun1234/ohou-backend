package com.manduljo.ohou.domain.cartItem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartItemUpdateQuantityDto {
    private Long cartItemId;
    private int quantiity;
}

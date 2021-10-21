package com.manduljo.ohou.domain.cart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartItemAddDto {
    private Long productId;
    private int quantity;
}

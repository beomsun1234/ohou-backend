package com.manduljo.ohou.domain.cart.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CartInfo {
    private int totalPrice;
    private List<CartIItemInfo> cartItems = new ArrayList<>();

    @Builder
    public CartInfo(List<CartIItemInfo> cartIItemInfos){
        this.totalPrice = cartIItemInfos.stream().mapToInt(CartIItemInfo::getTotalPrice).sum();
        this.cartItems = cartIItemInfos;
    }
}

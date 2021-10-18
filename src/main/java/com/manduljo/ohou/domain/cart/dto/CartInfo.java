package com.manduljo.ohou.domain.cart.dto;

import com.manduljo.ohou.domain.cart.Cart;
import com.manduljo.ohou.domain.cartItem.CartItem;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class CartInfo {
    private Long prodcutId;
    private Long cartItemId;
    private String productName;
    private String prodcutImage;
    private int price;
    private int quantity;
    @Builder
    public CartInfo(CartItem cartItem){
        this.prodcutId = cartItem.getProduct().getId();
        this.cartItemId = cartItem.getId();
        this.productName = cartItem.getName();
        this.price = cartItem.getPrice();
        this.prodcutImage = "ProductImage/"+cartItem.getProduct().getId()+"/"+"thumbnail.png";
        this.quantity = cartItem.getQuantity();
    }
}

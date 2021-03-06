package com.manduljo.ohou.domain.cartItem;

import com.manduljo.ohou.domain.BaseTimeEntity;
import com.manduljo.ohou.domain.cart.Cart;
import com.manduljo.ohou.domain.product.Product;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int productPrice;

    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public CartItem(int quantity, Product product){
        this.product = product;
        this.name = product.getName();
        this.productPrice = product.getPrice();
        this.quantity =quantity;
    }
    public void setCart(Cart cart){
        this.cart = cart;
    }

    public void addQuantity(int quantity){
        this.quantity += quantity;
    }
    public int getTotalPrice(){
        return this.productPrice*this.quantity;
    }

    public void updateQuantity(int quantity){
        this.quantity = quantity;
    }
}

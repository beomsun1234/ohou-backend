package com.manduljo.ohou.domain.cart;

import com.manduljo.ohou.domain.BaseTimeEntity;
import com.manduljo.ohou.domain.cartItem.CartItem;
import com.manduljo.ohou.domain.member.Member;
import com.manduljo.ohou.domain.product.Product;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Cart extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();

    @Builder
    public Cart(Member member){
        this.member = member;
    }

    public Cart addCartItems(CartItem cartItem){
        this.cartItems.add(cartItem);
        cartItem.setCart(this);
        return this;
    }
}

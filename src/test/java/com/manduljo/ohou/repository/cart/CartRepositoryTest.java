package com.manduljo.ohou.repository.cart;

import com.manduljo.ohou.domain.cart.Cart;
import com.manduljo.ohou.domain.cartItem.CartItem;
import com.manduljo.ohou.domain.member.Member;
import com.manduljo.ohou.repository.cartItem.CartItemRepository;
import com.manduljo.ohou.repository.member.MemberRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;




@DataJpaTest
class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("카트생성")
    void test_save(){
        //given
        Cart cart = Cart.builder()
                .member(memberRepository.save(Member.builder().email("test").name("test").build()))
                .build();
        //when
        Cart saveCart = cartRepository.save(cart);
        //then
        Assertions.assertThat(saveCart.getId()).isEqualTo(1L);
    }
    @Test
    @DisplayName("카트id로 검색")
    void test_addCartItem(){
        Cart cart = Cart.builder()
                .member(memberRepository.save(Member.builder().email("test").name("test").build()))
                .build();
        Cart saveCart = cartRepository.save(cart);
        //when
        Cart findCart = cartRepository.findById(saveCart.getId()).orElseThrow();
        //then
        Assertions.assertThat(findCart.getId()).isEqualTo(saveCart.getId());
    }
}
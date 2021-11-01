package com.manduljo.ohou.service;

import com.manduljo.ohou.domain.cart.Cart;
import com.manduljo.ohou.domain.cart.dto.CartInfo;
import com.manduljo.ohou.domain.cart.dto.CartItemAddDto;
import com.manduljo.ohou.domain.cartItem.CartItem;
import com.manduljo.ohou.domain.cartItem.dto.CartItemUpdateQuantityDto;
import com.manduljo.ohou.domain.member.Member;
import com.manduljo.ohou.domain.product.Product;
import com.manduljo.ohou.repository.cart.CartQueryRepository;
import com.manduljo.ohou.repository.cart.CartRepository;
import com.manduljo.ohou.repository.cartItem.CartItemRepository;
import com.manduljo.ohou.repository.member.MemberRepository;
import com.manduljo.ohou.repository.product.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class CartServiceTest {
    @Mock
    private CartRepository cartRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CartQueryRepository cartQueryRepository;
    @InjectMocks
    private CartService cartService;

    @Test
    @DisplayName("카트생성 및 추가, 카트에 상품 존재 안할경우")
    void test_createOrAddOrUpdateCart(){
        //given
        Long productFakeId = 1L;
        Long memberFakeId = 1L;
        Product product = Product.builder().id(productFakeId).name("test").price(10000).build();
        given(productRepository.findById(anyLong())).willReturn(java.util.Optional.ofNullable(product));
        Member member = Member.builder().email("test").id(memberFakeId).name("test").build();
        Cart cart = Cart.builder().member(member).build();
        given(cartQueryRepository.findByMemberId(anyLong())).willReturn(java.util.Optional.ofNullable(cart));
        given(cartRepository.save(any())).willReturn(cart);
        CartItemAddDto cartItemAddDto = CartItemAddDto.builder().productId(productFakeId).quantity(3).build();
        //when //then
        Long cartId = cartService.createOrAddOrUpdateCart(memberFakeId, cartItemAddDto);
    }
    @Test
    @DisplayName("카트생성 및 추가, 카트에 상품 존재할 경우")
    void test2_createOrAddOrUpdateCart(){
        Long productFakeId = 1L;
        Long memberFakeId = 1L;
        Product product = Product.builder().id(productFakeId).name("test").price(10000).build();
        given(productRepository.findById(anyLong())).willReturn(java.util.Optional.ofNullable(product));
        Member member = Member.builder().email("test").id(memberFakeId).name("test").build();
        Cart cart = Cart.builder().member(member).build();
        CartItem cartItem = CartItem.builder().quantity(2).product(product).build();
        cart.addCartItems(cartItem);
        given(cartQueryRepository.findByMemberId(anyLong())).willReturn(java.util.Optional.of(cart));
        given(cartRepository.save(any())).willReturn(cart);
        CartItemAddDto cartItemAddDto = CartItemAddDto.builder().productId(productFakeId).quantity(3).build();
        //when //then
        Long cartId = cartService.createOrAddOrUpdateCart(memberFakeId, cartItemAddDto);
    }

    @Test
    @DisplayName(" 장바구니 조회, 총 장바구니 상품 가격 체크")
    void test_findCartsByMemberId(){
        //given
        Long productFakeId = 1L;
        Long productFakeId2 = 2L;
        Long memberFakeId = 1L;
        Product product = Product.builder().id(productFakeId).name("test").price(10000).build();
        Product product2 = Product.builder().id(productFakeId2).name("test2").price(30000).build();
        Member member = Member.builder().email("test").id(memberFakeId).name("test").build();
        Cart cart = Cart.builder().member(member).build();
        CartItem cartItem = CartItem.builder().quantity(2).product(product).build();
        CartItem cartItem2 = CartItem.builder().quantity(3).product(product2).build();
        cart.addCartItems(cartItem);
        cart.addCartItems(cartItem2);
        given(cartQueryRepository.findByMemberId(anyLong())).willReturn(java.util.Optional.of(cart));
        //when
        CartInfo carts = cartService.findCartsByMemberId(memberFakeId);
        //then
        Assertions.assertThat(carts.getCartItems().size()).isEqualTo(2);
        Assertions.assertThat(carts.getTotalPrice()).isEqualTo(110000);
    }
    @Test
    @DisplayName(" 장바구니에 들어와서 직접 수정할 경우, 수령변경")
    void test_updateCartItem() {
        //given
        Long findFakeCartItemId = 1L;
        Long memberFakeId = 1L;
        Product product = Product.builder().id(1L).name("test").price(10000).build();
        Product product2 = Product.builder().id(2L).name("test2").price(30000).build();
        Member member = Member.builder().email("test").id(memberFakeId).name("test").build();
        Cart cart = Cart.builder().member(member).build();
        CartItem cartItem = CartItem.builder().quantity(2).product(product).build();
        CartItem cartItem2 = CartItem.builder().quantity(3).product(product2).build();
        cart.addCartItems(cartItem);
        cart.addCartItems(cartItem2);
        CartItemUpdateQuantityDto cartItemUpdateQuantityDto = CartItemUpdateQuantityDto.builder().cartItemId(findFakeCartItemId).quantity(5).build();
        given(cartQueryRepository.findByCartItemById(findFakeCartItemId,memberFakeId)).willReturn(java.util.Optional.ofNullable(cartItem));
        given(cartItemRepository.save(any())).willReturn(cartItem);
        //when
        cartService.updateCartItem(memberFakeId,cartItemUpdateQuantityDto);
        //then
        Assertions.assertThat(Objects.requireNonNull(cartItem).getQuantity()).isEqualTo(5);
    }
}
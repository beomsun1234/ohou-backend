package com.manduljo.ohou.service;


import com.manduljo.ohou.domain.cartItem.CartItem;
import com.manduljo.ohou.domain.orderItem.OrderItem;
import com.manduljo.ohou.domain.orders.Order;
import com.manduljo.ohou.domain.orders.dto.CartOrderDto;
import com.manduljo.ohou.domain.orders.dto.OrderDto;
import com.manduljo.ohou.domain.product.Product;
import com.manduljo.ohou.repository.cart.CartQueryRepository;
import com.manduljo.ohou.repository.order.OrderRepository;
import com.manduljo.ohou.repository.product.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CartQueryRepository cartQueryRepository;
    @Mock
    private CartService cartService;
    @InjectMocks
    private OrderService orderService;

    @Test
    @DisplayName("바로 구매")
    void test_createOrder(){
        //given
        Long fakeProductId = 1L;
        Long fakeMemberId = 1L;
        Product product = Product.builder().id(fakeProductId).price(20000).build();
        given(productRepository.findById(anyLong())).willReturn(java.util.Optional.ofNullable(product));
        OrderDto orderDto = OrderDto.builder().email("test").name("test").phone("010-0000-0000").productId(Collections.singletonList(fakeProductId)).quantity(2).build();
        OrderItem orderItem = OrderItem.builder().quantity(orderDto.getQuantity()).product(product).build();
        Order order = orderDto.toEntity(fakeMemberId, Collections.singletonList(orderItem));
        given(orderRepository.save(any())).willReturn(order);
        //when //then
        orderService.createOrder(fakeMemberId,orderDto);
    }

    @Test
    @DisplayName("장바구니 상품 단건 구매")
    void test_createCartOrder(){
        //given
        Long fakeCartItemId = 1L;
        Long fakeProductId = 1L;
        Long fakeMemberId = 1L;
        Product product = Product.builder().id(fakeProductId).price(20000).build();
        CartOrderDto cartOrderDto = CartOrderDto.builder().email("test").name("test").phone("010-0000-0000").cartItemIds(Collections.singletonList(fakeCartItemId)).build();
        CartItem cartItem = CartItem.builder().product(product).quantity(1).build();
        given(cartQueryRepository.findByMemberIdAndCartItemIdIn(fakeMemberId, Collections.singletonList(fakeCartItemId))).willReturn(Collections.singletonList(cartItem));
        OrderItem orderItem = OrderItem.builder().product(cartItem.getProduct()).quantity(cartItem.getQuantity()).build();
        Order order = cartOrderDto.toEntity(fakeMemberId, Collections.singletonList(orderItem));
        given(orderRepository.save(any())).willReturn(order);
        //when then
        orderService.createCartOrder(fakeMemberId,cartOrderDto);
    }

}
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartQueryRepository cartQueryRepository;
    private final CartService cartService;

    @Transactional
    public void createOrder(Long memberId, OrderDto orderDto){
        log.info("단건");
        //단건 일반 구매 = /checkout
        Product product = productRepository.findById(orderDto.getProductId().get(0)).orElseThrow();
        OrderItem orderItem = OrderItem.builder().product(product).quantity(orderDto.getQuantity()).build();
        Order order = orderDto.toEntity(memberId, Collections.singletonList(orderItem));
        orderRepository.save(order);
    }

    @Transactional
    public void createCartOrder(Long memberId, CartOrderDto cartOrderDto){
        log.info("장바구니 구매");
        //장바구니 구매시 /checkout?fromCart=true
        //선택된 장바구니 가져옴
        List<CartItem> cartItems = cartQueryRepository.findByMemberIdAndCartItemIdIn(memberId, cartOrderDto.getCartItemIds());
        if(cartItems.isEmpty()){
            //장바구니 아이템이 없을 경우
            throw new IllegalArgumentException("장바구니 아이템이 없습니다");
        }
        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> OrderItem.builder().product(cartItem.getProduct()).quantity(cartItem.getQuantity()).build())
                .collect(Collectors.toList());
        orderRepository.save(cartOrderDto.toEntity(memberId,orderItems));
        //주문 저장 후 카트아이템 삭제
        cartService.deleteCartItemByIdIn(cartItems.stream().map(CartItem::getId).collect(Collectors.toList()));
    }
}

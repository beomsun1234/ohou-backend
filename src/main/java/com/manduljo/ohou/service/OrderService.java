package com.manduljo.ohou.service;

import com.manduljo.ohou.domain.cart.Cart;
import com.manduljo.ohou.domain.cartItem.CartItem;
import com.manduljo.ohou.domain.member.Member;
import com.manduljo.ohou.domain.orderItem.OrderItem;
import com.manduljo.ohou.domain.orders.Order;
import com.manduljo.ohou.domain.product.Product;
import com.manduljo.ohou.repository.cart.CartQueryRepository;
import com.manduljo.ohou.repository.member.MemberRepository;
import com.manduljo.ohou.repository.order.OrderRepository;
import com.manduljo.ohou.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartQueryRepository cartQueryRepository;
    private final CartService cartService;

    @Transactional
    public void createOrder(int ck){
        //배송지 입력칸 추가
        //----바로구매시
        if(ck==1) {
            Member member = memberRepository.findById(1L).orElseThrow();
            log.info("단건");
            //단건 일반 구매 = /checkout
            Product product = productRepository.findById(1L).orElseThrow();
            OrderItem orderItem = OrderItem.builder().product(product).quantity(20).build();
            Order order = Order.builder().member(member).orderItem(Collections.singletonList(orderItem)).build();
            orderRepository.save(order);
        }
        //---- 장바구니 구매
        else {
            log.info("모두");
            //장바구니 구매시 /checkout?fromCart=true
            //선택된 장바구니 가져옴
            List<Long> itemIds = Arrays.asList(1L, 2L, 3L);// -> 프론트에서 선택된 장바구니 아이템 id 받아오기
            Cart cart = cartQueryRepository.findByCartItemByIdIn(itemIds).orElseThrow();
            List<OrderItem> orderItems = cart.getCartItems()
                    .stream()
                    .map(cartItem -> OrderItem.builder()
                            .product(cartItem.getProduct())
                            .quantity(cartItem.getQuantity())
                            .build())
                    .collect(Collectors.toList());
            orderRepository.save(Order.builder().member(cart.getMember()).orderItem(orderItems).build());
            //주문 저장 후 카트아이템 삭제(카트 아이템은 카트에서 관리한다.)
            cartService.deleteCartItemByIdIn(itemIds);
        }
    }
}

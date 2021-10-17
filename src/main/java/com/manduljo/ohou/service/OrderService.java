package com.manduljo.ohou.service;

import com.manduljo.ohou.domain.member.Member;
import com.manduljo.ohou.domain.orderItem.OrderItem;
import com.manduljo.ohou.domain.orders.Order;
import com.manduljo.ohou.domain.product.Product;
import com.manduljo.ohou.repository.member.MemberRepository;
import com.manduljo.ohou.repository.order.OrderRepository;
import com.manduljo.ohou.repository.orderItem.OrderItemRepository;
import com.manduljo.ohou.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public void createOrder(int ck){
        //----바로구매시
        Member member = memberRepository.findById(1L).orElseThrow();
        if(ck==1) {
            //단건 일반 구매 = /checkout
            Product product = productRepository.findById(1L).orElseThrow();
            OrderItem orderItem = OrderItem.builder().product(product).quantity(20).build();
            Order order = Order.builder().member(member).orderItem(Collections.singletonList(orderItem)).build();
            orderRepository.save(order);
        }
        //---- 장바구니 구매
        else {
            //장바구니 구매시 /checkout?fromCart=true
            List<Product> products = productRepository.findAll();
            List<OrderItem> orderItems = products.stream().map(product1 -> OrderItem.builder().product(product1).quantity(3).build()).collect(Collectors.toList());
            Order order1 = Order.builder().member(member).orderItem(orderItems).build();
            orderRepository.save(order1);
        }
    }
}

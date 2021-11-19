package com.manduljo.ohou.domain.orders.dto;

import com.manduljo.ohou.domain.member.Member;
import com.manduljo.ohou.domain.orderItem.OrderItem;
import com.manduljo.ohou.domain.orders.Order;
import com.manduljo.ohou.domain.orders.ShippingAddress;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderDto {
    private String name;
    private String email;
    private String phone;
    private ShippingAddress shippingAddress;
    private List<Long> productId;
    private int quantity;
    private int price;


    @Builder
    public OrderDto(String name, String email, String phone, ShippingAddress shippingAddress, List<Long> productId, int quantity, int price) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.shippingAddress = shippingAddress;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public Order toEntity(Long memberId, List<OrderItem> orderItems) {
        return Order.builder()
                .member(Member.builder().id(memberId).build())
                .shippingAddress(shippingAddress)
                .orderItem(orderItems).build();
    }
}

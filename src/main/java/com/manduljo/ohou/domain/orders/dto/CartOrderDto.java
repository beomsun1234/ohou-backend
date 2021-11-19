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
public class CartOrderDto {
    private String name;
    private String email;
    private String phone;
    private ShippingAddress shippingAddress;
    private List<Long> cartItemIds;

    @Builder
    public CartOrderDto(String name, String email, String phone, ShippingAddress shippingAddress, List<Long> cartItemIds) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.shippingAddress = shippingAddress;
        this.cartItemIds = cartItemIds;
    }

    public Order toEntity(Long memberId, List<OrderItem> orderItems) {
        return Order.builder()
                .member(Member.builder().id(memberId).build())
                .shippingAddress(shippingAddress)
                .orderItem(orderItems).build();
    }

}

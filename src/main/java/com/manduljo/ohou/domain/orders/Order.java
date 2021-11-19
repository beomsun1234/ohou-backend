package com.manduljo.ohou.domain.orders;

import com.manduljo.ohou.domain.BaseTimeEntity;
import com.manduljo.ohou.domain.member.Member;
import com.manduljo.ohou.domain.orderItem.OrderItem;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "ORDERS")
public class Order extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int price;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Embedded
    private  ShippingAddress shippingAddress;

    @Builder
    public Order(Member member, List<OrderItem> orderItem,ShippingAddress shippingAddress){
        this.member = member;
        this.orderItems = orderItem;
        addOrderItems(orderItem);
        this.price = getTotalPrice();
        this.shippingAddress = shippingAddress;
    }

    public void addOrderItems(List<OrderItem> orderItem){
        orderItem.forEach(orderItem1 -> orderItem1.setOrder(this));
    }

    public int getTotalPrice(){
        return this.orderItems.stream().mapToInt(orderItem1-> orderItem1.getPrice()*orderItem1.getQuantity()).sum();
    }
}
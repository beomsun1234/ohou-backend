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



    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    @Builder
    public Order(Member member, List<OrderItem> orderItem){
        this.member = member;
        this.price = orderItem.stream().mapToInt(orderItem1-> orderItem1.getPrice()*orderItem1.getQuantity()).sum();
        this.orderItems = orderItem;
        setOrderItems(orderItem);
    }

    public void setOrderItems(List<OrderItem> orderItem){
        orderItem.forEach(orderItem1 -> orderItem1.setOrder(this));
    }
}
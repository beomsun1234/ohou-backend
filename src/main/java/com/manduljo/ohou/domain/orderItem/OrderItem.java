package com.manduljo.ohou.domain.orderItem;

import com.manduljo.ohou.domain.BaseTimeEntity;
import com.manduljo.ohou.domain.orders.Order;
import com.manduljo.ohou.domain.product.Product;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class OrderItem extends BaseTimeEntity{
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int price;

    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    private Order order;

    @Builder
    public OrderItem(int quantity, Product product){
        this.name =product.getName();
        this.price =product.getPrice();
        this.quantity =quantity;
        this.product = product;
    }

    public void setOrder(Order order){
        this.order= order;
    }

}

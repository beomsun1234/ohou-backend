package com.manduljo.ohou.repository.order;

import com.manduljo.ohou.domain.orders.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

package com.manduljo.ohou.repository.orderItem;

import com.manduljo.ohou.domain.orderItem.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}

package com.manduljo.ohou.repository.cartItem;

import com.manduljo.ohou.domain.cartItem.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}

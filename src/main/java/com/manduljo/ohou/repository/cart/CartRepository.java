package com.manduljo.ohou.repository.cart;

import com.manduljo.ohou.domain.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByMemberId(Long id);
}

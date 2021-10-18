package com.manduljo.ohou.service;

import com.manduljo.ohou.domain.cart.Cart;
import com.manduljo.ohou.domain.cart.dto.CartInfo;
import com.manduljo.ohou.domain.cartItem.CartItem;
import com.manduljo.ohou.domain.product.Product;
import com.manduljo.ohou.repository.cart.CartQueryRepository;
import com.manduljo.ohou.repository.cart.CartRepository;
import com.manduljo.ohou.repository.member.MemberRepository;
import com.manduljo.ohou.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final CartQueryRepository cartQueryRepository;

    /**
     * 카트생성 및 추카
     * @param id
     */
    @Transactional
    public void createAndAddCart(Long id){
        Product product = productRepository.findById(3L).orElseThrow();
        CartItem cartItem = CartItem.builder().product(product).quantity(2).build();

        Cart cart = cartRepository.findByMemberId(id)
                .orElseGet(()->Cart.builder().member(memberRepository.findById(id)
                        .orElseThrow(()->new IllegalArgumentException("에러"))).build()).addCartItems(cartItem);

        cartRepository.save(cart);
    }

    /**
     * 장바구니 조회
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public List<CartInfo> findCartsByMemberId(Long id){
        Cart cart = cartQueryRepository.findByMemberId(id).orElseThrow(()-> new IllegalArgumentException("카트가없는 맴버입니다"));
        log.info("cart={}", cart.getId());
        return cart.getCartItems().stream()
                .map(cartItem -> CartInfo.builder().cartItem(cartItem).build())
                .collect(Collectors.toList());
    }

    /**
     * 카트 아이템 삭제(장바구니 구매시)
     * @param cartItemIds
     *
     */
    @Transactional
    public void deleteCartItemByIdIn(List<Long> cartItemIds){
        cartQueryRepository.deleteCartItemByIdIn(cartItemIds);
    }
}

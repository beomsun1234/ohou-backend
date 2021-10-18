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
import java.util.concurrent.atomic.AtomicInteger;
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
     * 카트생성 및 추가(카트에 같은 상품이 존재할 경우 수량만 업데이트)
     * @param id
     */
    @Transactional
    public Long createAndAddCart(Long id){
        AtomicInteger checkProduct = new AtomicInteger();
        Product product = productRepository.findById(1L).orElseThrow();
        //카트 추가시 없으면 만듬
        Cart cart = cartQueryRepository.findByMemberId(id) //fetch조인을 통해 카트 아이템과 상품을 모두 영속성
                .orElseGet(()->Cart.builder().member(memberRepository.findById(id)
                        .orElseThrow(()->new IllegalArgumentException("에러"))).build());

        if(cart.getCartItems().isEmpty()){
            cart.addCartItems(CartItem.builder().product(product).quantity(3).build());
            return cartRepository.save(cart).getId();
        }

        cart.getCartItems().forEach(cartItem -> {
            if(cartItem.getProduct().equals(product)){
                //동일한 상품이 있을경우 수량 변경
                checkProduct.set(1);
                cartItem.updateQuantity(3);
            }
        });
        if(checkProduct.get()!=1){
            //동일한 상품이 없으면 카트 추가
            return cartRepository.save(cart.addCartItems(CartItem.builder().product(product).quantity(2).build())).getId();
        }

        return cartRepository.save(cart).getId();
    }

    /**
     * 장바구니 조회
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public List<CartInfo> findCartsByMemberId(Long id){
        //카트 조회시 없으면 만듬
        Cart cart = cartQueryRepository.findByMemberId(id)
                .orElseGet(()-> cartRepository.save(Cart.builder()
                        .member(memberRepository.findById(id)
                                .orElseThrow(()-> new IllegalArgumentException("없는 화원입니다")))
                        .build()));
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

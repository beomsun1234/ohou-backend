package com.manduljo.ohou.service;

import com.manduljo.ohou.domain.cart.Cart;
import com.manduljo.ohou.domain.cart.dto.CartIItemInfo;
import com.manduljo.ohou.domain.cart.dto.CartInfo;
import com.manduljo.ohou.domain.cart.dto.CartItemAddDto;
import com.manduljo.ohou.domain.cartItem.CartItem;
import com.manduljo.ohou.domain.cartItem.dto.CartItemUpdateQuantityDto;
import com.manduljo.ohou.domain.product.Product;
import com.manduljo.ohou.repository.cart.CartQueryRepository;
import com.manduljo.ohou.repository.cart.CartRepository;
import com.manduljo.ohou.repository.cartItem.CartItemRepository;
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
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartQueryRepository cartQueryRepository;
    /**
     * 카트생성 및 추가(카트에 같은 상품이 존재할 경우 수량만 추가)
     * @param
     */
    @Transactional
    public Long createOrAddOrUpdateCart(CartItemAddDto cartItemAddDto){
        //맴버 id, 상품 id, 수량
        Product product = productRepository.findById(cartItemAddDto.getProductId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
        //카트 추가시 해당 맴버가 카트가 존재하지 않을경우 카트 생성
        Cart cart = cartQueryRepository.findByMemberId(cartItemAddDto.getMemberId()) //fetch조인을 통해 카트 아이템과 상품을 모두 영속성
                .orElseGet(()->Cart.builder().member(memberRepository.findById(cartItemAddDto.getMemberId())
                        .orElseThrow(()->new IllegalArgumentException("에러"))).build());

        return checkCartItem(cart,product,cartItemAddDto.getQuantity());
    }
    /**
     * 카트에 해당 상품이 있는지 없는 체크하는 로직
     * 상품이 존재 할 경우 수량만 추가, 존재하지 않을경우 아이템 추가
     * @param cart
     * @param product
     * @return
     */
    private Long checkCartItem(Cart cart, Product product, int quantity){
        AtomicInteger checkProduct = new AtomicInteger();
        cart.getCartItems().stream()
                .filter(cartItem1 -> cartItem1.getProduct().equals(product))
                .findFirst()
                .ifPresent(
                        //상품이 존재 할 경우 수량 추가
                        cartItem1 -> {
                            cartItem1.addQuantity(quantity);
                            checkProduct.set(1);
                            log.info("상품이 존재 합니다.");
                        }
                );
        //카트에 아이템에 해당 상품이 없을 경우 카트에 추가
        if(cart.getCartItems().isEmpty()|| checkProduct.get()!=1){
            CartItem cartItem = CartItem.builder().product(product).quantity(quantity).build();
            log.info("상품이 존재하지 않습니다. 추가합니다");
            return cartRepository.save(cart.addCartItems(cartItem)).getId();
        }
        return cartRepository.save(cart).getId();
    }

    /**
     * 장바구니 조회
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public CartInfo findCartsByMemberId(Long id){
        //카트 조회시 없으면 만듬
        Cart cart = cartQueryRepository.findByMemberId(id)
                .orElseGet(()-> cartRepository.save(Cart.builder()
                        .member(memberRepository.findById(id)
                                .orElseThrow(()-> new IllegalArgumentException("없는 화원입니다")))
                        .build()));
        return CartInfo.builder().cartIItemInfos(cart.getCartItems().stream()
                .map(cartItem -> CartIItemInfo.builder().cartItem(cartItem).build())
                .collect(Collectors.toList())).build();
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

    /**
     * 장바구니에 들어와서 직접 수정할 경우 해당
     * @return
     */
    @Transactional
    public Long updateCartItem(CartItemUpdateQuantityDto cartItemUpdateQuantityDto){
        CartItem cartItem = cartQueryRepository.findByCartItemById(cartItemUpdateQuantityDto.getCartItemId()).orElseThrow();
        cartItem.updateQuantity(cartItemUpdateQuantityDto.getQuantiity());
        return cartItemRepository.save(cartItem).getId();
    }
}

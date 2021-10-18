package com.manduljo.ohou.repository.cart;

import com.manduljo.ohou.domain.cart.Cart;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.manduljo.ohou.domain.cart.QCart.cart;
import static com.manduljo.ohou.domain.cartItem.QCartItem.cartItem;
import static com.manduljo.ohou.domain.product.QProduct.product;
@RequiredArgsConstructor
@Repository
public class CartQueryRepository {
    private final JPAQueryFactory queryFactory;

    //장바구니 조회(최신 등록순으로)
    public Optional<Cart> findByMemberId(Long id){
        return Optional.ofNullable(queryFactory.selectFrom(cart)
                .distinct()
                .join(cart.cartItems, cartItem).fetchJoin()
                .join(cartItem.product, product).fetchJoin()
                .where(cart.member.id.eq(id))
                .orderBy(cartItem.createdDate.desc())
                .fetchOne());
    }

    //장바구니상품 구매
    public Optional<Cart> findByCartItemIdIn(List<Long> ids){
        return Optional.ofNullable(queryFactory.selectFrom(cart)
                .join(cart.cartItems, cartItem).fetchJoin()
                .join(cartItem.product, product).fetchJoin()
                .where(cartItem.id.in(ids))
                .fetchOne());
    }

    //장바구니상품 삭제
    public void deleteCartItemByIdIn(List<Long> ids){
        queryFactory.delete(cartItem).where(cartItem.id.in(ids)).execute();
    }

}

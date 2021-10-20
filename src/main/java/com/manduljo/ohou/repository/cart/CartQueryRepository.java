package com.manduljo.ohou.repository.cart;

import com.manduljo.ohou.domain.cart.Cart;
import com.manduljo.ohou.domain.cartItem.CartItem;
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
                .leftJoin(cart.cartItems, cartItem).fetchJoin()
                .where(cart.member.id.eq(id))
                .orderBy(cartItem.createdDate.desc())
                .fetchOne());
    }


    /**
     * 장바구니 아이템 수정시
     * @param id
     * @return
     */
    public Optional<CartItem> findByCartItemById(Long id){
        return Optional.ofNullable(queryFactory.selectFrom(cartItem)
                .join(cartItem.cart, cart)
                .where(cartItem.id.eq(id))
                .fetchOne());
    }

    /**
     * 장바구니 주문일 경우
     * @param id
     * @param ids
     * @return
     */
    public List<CartItem> findByMemberIdAndCartItemIdIn(Long id,List<Long>ids){
        return queryFactory.selectFrom(cartItem)
                .join(cartItem.product).fetchJoin()
                .where(cartItem.id.in(ids),
                        cartItem.cart.member.id.eq(id))
                .fetch();
    }

    //장바구니상품 삭제
    public void deleteCartItemByIdIn(List<Long> ids){
        queryFactory.delete(cartItem).where(cartItem.id.in(ids)).execute();
    }

    //단건 삭제
    public void deleteOneCartItemById(Long id){
        queryFactory.delete(cartItem).where(cartItem.id.eq(id)).execute();
    }

}

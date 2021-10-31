package com.manduljo.ohou.repository.cart;

import com.manduljo.ohou.domain.cart.Cart;
import com.manduljo.ohou.domain.cartItem.CartItem;
import com.manduljo.ohou.domain.category.ProductCategory;
import com.manduljo.ohou.domain.member.Member;
import com.manduljo.ohou.domain.product.Product;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.manduljo.ohou.domain.cart.QCart.cart;
import static com.manduljo.ohou.domain.cartItem.QCartItem.cartItem;

@DataJpaTest
class CartQueryRepositoryTest {
    @Autowired
    private EntityManager em;


    private JPAQueryFactory queryFactory;

    @BeforeEach
    void init(){
        queryFactory = new JPAQueryFactory(em);
    }

    @Test
    @DisplayName("유저 장바구니 조회")
    void test_findByMemberId(){
        //given
        Member member = Member.builder().name("test").email("test").build();
        em.persist(member);
        Cart saveCart = Cart.builder().member(member).build();
        em.persist(saveCart);
        ProductCategory category = ProductCategory.builder().id("0").name("test").build();
        em.persist(category);
        Product product = Product.builder().price(1000).name("test").productCategory(category).build();
        em.persist(product);
        CartItem saveCartItem = CartItem.builder().product(product).quantity(3).build();
        em.persist(saveCartItem);
        saveCart.addCartItems(saveCartItem);
        saveCartItem.setCart(saveCart);
        Long memberId = member.getId();
        //when
        Cart findCart = Optional.ofNullable(queryFactory.selectFrom(cart)
                .distinct()
                .leftJoin(cart.cartItems, cartItem).fetchJoin()
                .where(cart.member.id.eq(memberId))
                .orderBy(cartItem.createdDate.desc())
                .fetchOne()).orElseThrow();
        //then
        Assertions.assertThat(findCart.getCartItems().size()).isEqualTo(1);
        Assertions.assertThat(findCart.getCartItems().get(0).getName()).isEqualTo("test");
    }
    @Test
    @DisplayName("유저 장바구니 아이템 조회, 장바구니 아이템 수정시")
    void test_findByCartItemById(){
        //give
        Member member = Member.builder().name("test").email("test").build();
        em.persist(member);
        Cart saveCart = Cart.builder().member(member).build();
        em.persist(saveCart);
        ProductCategory category = ProductCategory.builder().id("0").name("test").build();
        em.persist(category);
        Product product = Product.builder().price(1000).name("test").productCategory(category).build();
        em.persist(product);
        CartItem saveCartItem = CartItem.builder().product(product).quantity(3).build();
        em.persist(saveCartItem);
        saveCart.addCartItems(saveCartItem);
        saveCartItem.setCart(saveCart);
        Long memberId = member.getId();
        Long cartItemId = saveCartItem.getId();
        //when
        CartItem findCartItem = Optional.ofNullable(queryFactory.selectFrom(cartItem)
                .join(cartItem.cart, cart)
                .where(cartItem.id.eq(cartItemId), cart.member.id.eq(memberId))
                .fetchOne()).orElseThrow();
        //then
        Assertions.assertThat(findCartItem.getName()).isEqualTo("test");
    }

    @Test
    @DisplayName("유저 장바구니 아이템 조회, 장바구니 주문일 경우")
    void test_findByMemberIdAndCartItemIdIn() {
        //given
        Member member = Member.builder().name("test").email("test").build();
        em.persist(member);
        Cart saveCart = Cart.builder().member(member).build();
        em.persist(saveCart);
        ProductCategory category = ProductCategory.builder().id("0").name("test").build();
        em.persist(category);
        Product product = Product.builder().price(10000).name("test").productCategory(category).build();
        em.persist(product);
        Product product2 = Product.builder().price(20000).name("test2").productCategory(category).build();
        em.persist(product2);
        CartItem saveCartItem = CartItem.builder().product(product).quantity(3).build();
        em.persist(saveCartItem);
        saveCart.addCartItems(saveCartItem);
        saveCartItem.setCart(saveCart);
        CartItem saveCartItem2 = CartItem.builder().product(product2).quantity(2).build();
        em.persist(saveCartItem2);
        saveCart.addCartItems(saveCartItem2);
        saveCartItem.setCart(saveCart);
        Long memberId = member.getId();
        List<Long> ids = Arrays.asList(saveCartItem.getId(), saveCartItem2.getId());
        //when
        List<CartItem> itemList = queryFactory.selectFrom(cartItem)
                .join(cartItem.product).fetchJoin()
                .where(cartItem.id.in(ids),
                        cartItem.cart.member.id.eq(memberId))
                .fetch();
        int totalPrice = itemList.stream().mapToInt(cartItem1 -> cartItem1.getQuantity() * cartItem1.getProductPrice()).sum();
        //then
        Assertions.assertThat(itemList.size()).isEqualTo(2);
        Assertions.assertThat(totalPrice).isEqualTo(70000);
    }
    @Test
    @DisplayName("유저 장바구니 아이템 삭제, 장바구니 구매시")
    void test_deleteCartItemByIdIn(){
        //given
        Member member = Member.builder().name("test").email("test").build();
        em.persist(member);
        Cart saveCart = Cart.builder().member(member).build();
        em.persist(saveCart);
        ProductCategory category = ProductCategory.builder().id("0").name("test").build();
        em.persist(category);
        Product product = Product.builder().price(10000).name("test").productCategory(category).build();
        em.persist(product);
        Product product2 = Product.builder().price(20000).name("test2").productCategory(category).build();
        em.persist(product2);
        CartItem saveCartItem = CartItem.builder().product(product).quantity(3).build();
        em.persist(saveCartItem);
        saveCart.addCartItems(saveCartItem);
        saveCartItem.setCart(saveCart);
        CartItem saveCartItem2 = CartItem.builder().product(product2).quantity(2).build();
        em.persist(saveCartItem2);
        saveCart.addCartItems(saveCartItem2);
        saveCartItem.setCart(saveCart);
        List<Long> ids = Arrays.asList(saveCartItem.getId(), saveCartItem2.getId());
        //when
        long deleteCount = queryFactory.delete(cartItem).where(cartItem.id.in(ids)).execute();
        //then
        Assertions.assertThat(deleteCount).isEqualTo(2);
    }

    @Test
    @DisplayName("유저 장바구니 아이템 삭제, 장바구니에서 삭제시")
    void test_deleteOneCartItemById(){
        //given
        Member member = Member.builder().name("test").email("test").build();
        em.persist(member);
        Cart saveCart = Cart.builder().member(member).build();
        em.persist(saveCart);
        ProductCategory category = ProductCategory.builder().id("0").name("test").build();
        em.persist(category);
        Product product = Product.builder().price(10000).name("test").productCategory(category).build();
        em.persist(product);
        Product product2 = Product.builder().price(20000).name("test2").productCategory(category).build();
        em.persist(product2);
        CartItem saveCartItem = CartItem.builder().product(product).quantity(3).build();
        em.persist(saveCartItem);
        saveCart.addCartItems(saveCartItem);
        saveCartItem.setCart(saveCart);
        CartItem saveCartItem2 = CartItem.builder().product(product2).quantity(2).build();
        em.persist(saveCartItem2);
        saveCart.addCartItems(saveCartItem2);
        saveCartItem.setCart(saveCart);
        Long deleteCartItemId = saveCartItem2.getId();
        List<Long> ids = Arrays.asList(saveCartItem.getId(), saveCartItem2.getId());
        //when
        queryFactory.delete(cartItem).where(cartItem.id.eq(deleteCartItemId)).execute();
        List<CartItem> itemList = queryFactory.selectFrom(cartItem)
                .join(cartItem.product).fetchJoin()
                .where(cartItem.id.in(ids),
                        cartItem.cart.member.id.eq(member.getId()))
                .fetch();
        //then
        Assertions.assertThat(itemList.size()).isEqualTo(1);
    }
}
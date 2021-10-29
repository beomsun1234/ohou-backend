package com.manduljo.ohou.repository.product;

import com.manduljo.ohou.domain.category.ProductCategory;
import com.manduljo.ohou.domain.product.Product;
import com.manduljo.ohou.domain.product.QProduct;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.manduljo.ohou.domain.product.QProduct.product;


@DataJpaTest
class ProductQueryRepositoryTest {

    @Autowired
    private EntityManager em;


    private JPAQueryFactory queryFactory;

    @BeforeEach
    void init(){
        queryFactory = new JPAQueryFactory(em);
        ProductCategory pCategory = ProductCategory.builder().id("0").name("가구(부모)").build();
        em.persist(pCategory);
        ProductCategory category = ProductCategory.builder().id("0_1").name("소파").parentCategory(pCategory).build();
        em.persist(category);
        Product saveProduct = Product.builder().name("고급소파").price(10000).productCategory(category).build();
        Product saveProduct2 = Product.builder().name("저급소파").price(5000).productCategory(category).build();
        em.persist(saveProduct);
        em.persist(saveProduct2);
    }

    @DisplayName("상품id 단건 조회")
    @Test
    void test_findById(){
        //given

        //when
        Product findProduct = Optional.ofNullable(queryFactory
                .selectFrom(product)
                .where(product.id.eq(1L))
                .fetchOne()).orElseThrow();
        //then
        Assertions.assertThat(findProduct.getName()).isEqualTo("고급소파");
    }


    @DisplayName("스토어 카테고리 클릭시 부모카테고리 아이디가 0인 모든 상품조회")
    @Test
    void test_findByCategoryId(){
        //when
        List<Product> products = queryFactory
                .selectFrom(product)
                .where(eqCategoryId("0")).fetch();
        //then
        Assertions.assertThat(products.size()).isEqualTo(2);
    }

    @DisplayName("스토어 카테고리 클릭시 카테고리 아이디가 0_1인 모든 상품조회")
    @Test
    void test2_findByCategoryId(){
        //when
        List<Product> products = queryFactory
                .selectFrom(product)
                .where(eqCategoryId("0_1")).fetch();
        //then
        Assertions.assertThat(products.size()).isEqualTo(2);
    }
    private BooleanExpression eqCategoryId(String id){
        if(id!=null){
            if(id.contains("_")){
                return product.productCategory.id.eq(id);
            }
            return product.productCategory.parentCategory.id.eq(id);
        }
        return null;
    }

    @DisplayName("동적쿼리, 상품이름 검색")
    @Test
    void test_findByProdocutNameOrCategoryNameOrParentCategoryNameContaining(){
        //given
        String searchText = "소파";
        //when
        List<Product> products = queryFactory
                .selectFrom(product)
                .where(containsProductName(searchText)
                        .or(containsCategoryName(searchText))
                        .or(containsParentCategoryName(searchText)))
                .fetch();
        //then
        Assertions.assertThat(products.size()).isEqualTo(2);
    }

    @DisplayName("동적쿼리, 부모카테고리 검색")
    @Test
    void test2_findByProdocutNameOrCategoryNameOrParentCategoryNameContaining(){
        //given
        String searchText = "가구(부모)";
        //when
        List<Product> products = queryFactory
                .selectFrom(product)
                .where(containsProductName(searchText)
                        .or(containsCategoryName(searchText))
                        .or(containsParentCategoryName(searchText)))
                .fetch();
        //then
        Assertions.assertThat(products.size()).isEqualTo(2);
    }
    private BooleanExpression containsProductName(String searchText){
        if(searchText!=null){
            return product.name.contains(searchText);
        }
        return null;
    }
    private BooleanExpression containsCategoryName(String searchText){
        if(searchText!=null){
            return product.productCategory.name.contains(searchText);
        }
        return null;
    }
    private BooleanExpression containsParentCategoryName(String searchText){
        if(searchText!=null){
            return product.productCategory.parentCategory.name.contains(searchText);
        }
        return null;
    }
}
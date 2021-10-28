package com.manduljo.ohou.repository.category;

import com.manduljo.ohou.domain.category.ProductCategory;
import com.manduljo.ohou.domain.category.QProductCategory;
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


@DataJpaTest
class ProductCategoryQueryRepositoryTest {


    @Autowired
    private EntityManager em;


    private JPAQueryFactory queryFactory;

    @BeforeEach
    void init(){
        queryFactory = new JPAQueryFactory(em);
    }

    private final QProductCategory productCategory = QProductCategory.productCategory;

    @Test
    @DisplayName("모든 카테고리 조회")
    void test_findAll(){
        //given
        ProductCategory category = ProductCategory.builder().id("0").name("가구").build();
        ProductCategory category2 = ProductCategory.builder().id("1").name("패브릭").build();
        em.persist(category);
        em.persist(category2);
        //then
        List<ProductCategory> categories = queryFactory
                .selectFrom(productCategory)
                .fetch();
        //given
        Assertions.assertThat(categories.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("서브 카테고리찾기")
    void test_findCategoryByParentId() {
        //given
        ProductCategory categoryParent = ProductCategory.builder().id("0").name("가구").build();
        ProductCategory categoryParent2 = ProductCategory.builder().id("1").name("패브릭").build();
        em.persist(categoryParent);
        em.persist(categoryParent2);

        ProductCategory subCategory = ProductCategory.builder().id("0_1").name("소파").parentCategory(categoryParent).build();
        em.persist(subCategory);
        //when
        List<ProductCategory> categories = queryFactory
                .selectFrom(productCategory)
                .where(productCategory.parentCategory.id.eq(categoryParent.getId()))
                .fetch();
        //then
        Assertions.assertThat(categories.size()).isEqualTo(1);
        Assertions.assertThat(categories.get(0).getName()).isEqualTo("소파");
    }

    @Test
    @DisplayName("카테고리 id로 검색(BooleanExpression)")
    void test_findByIdOrName(){
        ProductCategory categoryParent = ProductCategory.builder().id("0").name("가구").build();
        ProductCategory categoryParent2 = ProductCategory.builder().id("1").name("패브릭").build();
        em.persist(categoryParent);
        em.persist(categoryParent2);
        String searchText = "0";
        //when
        ProductCategory category = Optional.ofNullable(queryFactory.selectFrom(this.productCategory)
                .where(eqCategoryId(searchText)
                        .or(eqCategoryName(searchText)))
                .fetchOne()).orElseThrow();
        //then
        Assertions.assertThat(category.getName()).isEqualTo("가구");
    }
    @Test
    @DisplayName("카테고리 이름으로 검색(BooleanExpression)")
    void test2_findByIdOrName(){
        ProductCategory categoryParent = ProductCategory.builder().id("0").name("가구").build();
        ProductCategory categoryParent2 = ProductCategory.builder().id("1").name("패브릭").build();
        em.persist(categoryParent);
        em.persist(categoryParent2);
        String searchText = "패브릭";
        //when
        ProductCategory category = Optional.ofNullable(queryFactory.selectFrom(this.productCategory)
                .where(eqCategoryId(searchText)
                        .or(eqCategoryName(searchText)))
                .fetchOne()).orElseThrow();
        //then
        Assertions.assertThat(category.getName()).isEqualTo("패브릭");
    }



    private BooleanExpression eqCategoryName(String name){
        if(name!=null){
            return productCategory.name.eq(name);
        }
        return null;
    }
    private BooleanExpression eqCategoryId(String id){
        if(id!=null){
            return productCategory.id.eq(id);
        }
        return null;
    }
}
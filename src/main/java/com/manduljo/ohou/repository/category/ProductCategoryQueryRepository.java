package com.manduljo.ohou.repository.category;


import com.manduljo.ohou.domain.category.ProductCategory;
import com.manduljo.ohou.domain.category.QProductCategory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.manduljo.ohou.domain.category.QProductCategory.productCategory;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductCategoryQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<ProductCategory> findAll(){
        return queryFactory
                .selectFrom(productCategory)
                .fetch();
    }

    public List<ProductCategory> findCategoryByParentId(Long id){
        return queryFactory
                .selectFrom(productCategory)
                .where(productCategory.parentCategory.id.eq(id))
                .fetch();
    }
}

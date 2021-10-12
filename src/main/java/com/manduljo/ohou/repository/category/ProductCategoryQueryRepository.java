package com.manduljo.ohou.repository.category;


import com.manduljo.ohou.domain.category.ProductCategory;
import com.manduljo.ohou.domain.category.QProductCategory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.manduljo.ohou.domain.category.QProductCategory.productCategory;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductCategoryQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<ProductCategory> findAll(){
        return queryFactory
                .selectFrom(productCategory)
                .fetch();
    }

    //
    public Optional<ProductCategory> findById(String id){
        return Optional.ofNullable(queryFactory
                .selectFrom(productCategory)
                .where(productCategory.id.eq(id))
                .fetchFirst());
    }

    /**
     * id에 _ 포함되어있으면 서브카테고리 조회, 아니면 부모 조히
     */
    public List<ProductCategory> findByCategoryId(String id){
        return queryFactory
                .selectFrom(productCategory)
                .where(eqCategoryId(id))
                .fetch();
    }

    /**
     * 서브카테고리
     * @param id
     * @return
     */
    public List<ProductCategory> findCategoryByParentId(String id){
        return queryFactory
                .selectFrom(productCategory)
                .where(productCategory.parentCategory.id.eq(id))
                .fetch();
    }

    /**
     * 메인카테고리(루트가없는)
     * @return
     */
    public List<ProductCategory> findMainCategory(){
        return queryFactory
                .selectFrom(productCategory)
                .where(productCategory.parentCategory.id.isNull())
                .fetch();
    }
    private BooleanExpression eqCategoryId(String id){
        if(id!=null){
            if(id.contains("_")){
                return productCategory.id.eq(id);
            }
            return productCategory.parentCategory.id.eq(id);
        }
        return null;
    }

}

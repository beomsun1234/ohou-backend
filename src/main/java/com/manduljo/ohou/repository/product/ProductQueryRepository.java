package com.manduljo.ohou.repository.product;


import com.manduljo.ohou.domain.product.Product;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

import static com.manduljo.ohou.domain.category.QProductCategory.productCategory;
import static com.manduljo.ohou.domain.product.QProduct.product;
@Repository
@RequiredArgsConstructor
public class ProductQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<Product> findByParentId(String id){
        return queryFactory
                .selectFrom(product)
                .where(product.productCategory.parentCategory.id.eq(id))
                .fetch();
    }

    public List<Product> findByCategoryId(String id){
        return queryFactory
                .selectFrom(product)
                .where(eqCategoryId(id))
                .fetch();
    }

    //동적쿼리 상품이름, 카테고리이름 검색
    public List<Product> findByProdocutNameOrCategoryNameOrParentCategoryNameContaining(String searchText){
        return queryFactory
                .selectFrom(product)
                .where(containsProductName(searchText)
                        .or(containsCategoryName(searchText))
                        .or(containsParentCategoryName(searchText)))
                .fetch();
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
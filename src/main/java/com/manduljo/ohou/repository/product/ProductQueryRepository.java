package com.manduljo.ohou.repository.product;


import com.manduljo.ohou.domain.product.Product;
import com.manduljo.ohou.domain.product.dto.ProductInfo;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.manduljo.ohou.domain.category.QProductCategory.productCategory;
import static com.manduljo.ohou.domain.product.QProduct.product;
@Repository
@RequiredArgsConstructor
public class ProductQueryRepository {
    private final JPAQueryFactory queryFactory;

    //--상품id 단건 조회
    public Optional<Product> findById(Long id){
        return Optional.ofNullable(queryFactory
                .selectFrom(product)
                .where(eqProductId(id))
                .fetchOne());
    }

    //
    //--카테고리 조회
    public List<Product> findByParentId(String id){
        return queryFactory
                .selectFrom(product)
                .where(product.productCategory.parentCategory.id.eq(id))
                .fetch();
    }

    //리미트15
    public Page<Product> findByCategoryId(Pageable pageable,String id){
        QueryResults<Product> results = queryFactory
                .selectFrom(product)
                .where(eqCategoryId(id))
                .orderBy(product.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(15)
                .fetchResults();
        List<Product> findProducts = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(findProducts,pageable,total);
    }
    //-------------------------


    //동적쿼리 상품이름, 카테고리이름 검색
    public Page<Product> findByProdocutNameOrCategoryNameOrParentCategoryNameContaining(Pageable pageable, String searchText){
        QueryResults<Product> productQueryResults = queryFactory
                .selectFrom(product)
                .where(containsProductName(searchText)
                        .or(containsCategoryName(searchText))
                        .or(containsParentCategoryName(searchText)))
                .orderBy(product.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(15)
                .fetchResults();
        List<Product> findProducts = productQueryResults.getResults();
        long total = productQueryResults.getTotal();
        return new PageImpl<>(findProducts,pageable,total);
    }
    //-------------------

    /**
     * 괸라자용 상품검색(상품 이름으로 검색만)
     * @param productName
     * @return
     */
    public List<Product> findByProductName(String productName){
        return queryFactory.selectFrom(product)
                .where(containsProductName(productName))
                .orderBy(product.createdDate.desc())
                .fetch();
    }

    private BooleanExpression eqProductId(Long id){
        if(id!=null){
            return product.id.eq(id);
        }
        return null;
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

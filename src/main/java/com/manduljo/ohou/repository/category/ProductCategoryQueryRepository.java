package com.manduljo.ohou.repository.category;


import com.manduljo.ohou.domain.category.ProductCategory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


import static com.manduljo.ohou.domain.category.QProductCategory.productCategory;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductCategoryQueryRepository {
    private final JPAQueryFactory queryFactory;


    public Optional<ProductCategory> findByIdOrName(String id, String name){
        return Optional.ofNullable(queryFactory.selectFrom(productCategory)
                .where(eqCategoryId(id)
                        .or(eqCategoryName(name)))
                .fetchOne());

    }

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
                .fetchOne());
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

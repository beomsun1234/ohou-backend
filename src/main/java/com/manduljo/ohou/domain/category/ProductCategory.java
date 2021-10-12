package com.manduljo.ohou.domain.category;

import com.manduljo.ohou.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductCategory extends BaseTimeEntity implements Persistable<String> {
    @Id
    private String id;

    //카테고리 이름
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_product_category_id")
    private ProductCategory parentCategory;


    @OneToMany(mappedBy = "parentCategory",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProductCategory> subCategory = new LinkedHashSet<>();

    @Builder
    public ProductCategory(String id, String name,ProductCategory parentCategory) {
        this.id = id;
        this.name = name;
        this.parentCategory = parentCategory;
    }


    //카테고리.findById() 에 의해 데이타의 존재여부를 확인할 수 있음에도, 카테고리.save() 에서 또 다시 select 를 수행한다.
    //식별자를 직접할당하기에 merge가 된다. (식별자가 없으면 persist)
    @Override
    public boolean isNew() {
        return  getCreatedDate() == null;
    }
}

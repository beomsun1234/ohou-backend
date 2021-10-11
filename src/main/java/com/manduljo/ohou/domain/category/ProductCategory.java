package com.manduljo.ohou.domain.category;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //카테고리 이름
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_product_category_id")
    private ProductCategory parentCategory;


    @OneToMany(mappedBy = "parentCategory",cascade = CascadeType.ALL)
    private List<ProductCategory> subCategory = new ArrayList<>();

    @Builder
    public ProductCategory(String name,ProductCategory parentCategory) {
        this.name = name;
        this.parentCategory = parentCategory;
    }
}

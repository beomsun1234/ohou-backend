package com.manduljo.ohou.domain.product;

import com.manduljo.ohou.domain.BaseTimeEntity;
import com.manduljo.ohou.domain.category.ProductCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String price;

    private String thumbnailImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_category_id")
    private ProductCategory productCategory;

    @Builder
    public Product(String name, String price, String thumbnailImage, ProductCategory productCategory){
        this.name = name;
        this.price = price;
        this.thumbnailImage = thumbnailImage;
        this.productCategory = productCategory;
    }
}

package com.manduljo.ohou.domain.product.dto;

import com.manduljo.ohou.domain.category.ProductCategory;
import com.manduljo.ohou.domain.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductCreateDto {
    private String category_id;
    private String name;
    private int price;

    @Builder
    public ProductCreateDto(String category_id, String name, int price){
        this.category_id = category_id;
        this.name = name;
        this.price =price;
    }


    public Product toEntity(ProductCategory productCategory){
        return Product.builder()
                .price(price)
                .name(name)
                .productCategory(productCategory)
                .build();
    }

}

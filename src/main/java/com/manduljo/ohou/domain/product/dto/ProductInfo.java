package com.manduljo.ohou.domain.product.dto;

import com.manduljo.ohou.domain.category.ProductCategory;
import com.manduljo.ohou.domain.category.dto.CategoryInfo;
import com.manduljo.ohou.domain.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class ProductInfo implements Serializable {
    private Long id;
    private String name;
    private int price;
    private String thumbnailImage;
    @Builder
    public ProductInfo(Product product){
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.thumbnailImage = product.getThumbnailImage();
    }
}

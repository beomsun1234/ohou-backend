package com.manduljo.ohou.domain.product.dto;

import com.manduljo.ohou.domain.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProductDetail {
    private Long id;
    private String name;
    private String price;
    private String thumbnail;
    private List<String> productImg;
    // private List<String>productImage -> 상품 상세 이미지는 나중에
    @Builder
    public ProductDetail(Product entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.thumbnail = entity.getThumbnailImage();
        this.productImg = entity.getProductImage();
    }
}

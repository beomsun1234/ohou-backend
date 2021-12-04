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
    private int price;
    private String thumbnail;
    private List<String> leftCoverImages;
    private String mainImages;
    private String productDetailImage;
    // private List<String>productImage -> 상품 상세 이미지는 나중에
    @Builder
    public ProductDetail(Product entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.thumbnail = entity.getThumbnailImage();
        this.leftCoverImages = entity.getLeftCoverImage();
        this.mainImages = entity.getMainImage();
        this.productDetailImage = entity.getProductDetailImage();
    }
}

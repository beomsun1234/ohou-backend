package com.manduljo.ohou.domain.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ProductPageDto implements Serializable {
    private int totalPage;
    private int totalCount;
    private List<ProductInfo> productInfos = new ArrayList<>();

    @Builder
    public ProductPageDto(int totalPage, int totalCount, List<ProductInfo> productInfoList) {
        this.totalPage = totalPage;
        this.totalCount = totalCount;
        this.productInfos = productInfoList;
    }
}

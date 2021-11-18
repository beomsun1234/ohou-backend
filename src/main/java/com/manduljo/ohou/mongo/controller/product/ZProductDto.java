package com.manduljo.ohou.mongo.controller.product;

import lombok.*;

import java.util.List;

public class ZProductDto {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class FindProductResponse {
    private List<ProductItem> productList;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class ProductItem {
      private String id;
      private String productName;
      private String categoryId;
    }

  }

}

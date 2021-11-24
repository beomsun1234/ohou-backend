package com.manduljo.ohou.mongo.controller.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.manduljo.ohou.mongo.service.product.ZProductCriteria;
import lombok.*;

import java.util.List;

public class ZProductDto {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class GetProductDetailResponse {
    private String id;

    @JsonProperty("name")
    private String productName;
    private int price;

    @JsonProperty("thumbnail")
    private String thumbnailImage;

    @JsonProperty("productImg")
    private List<String> productImageList;
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class FindProductResponse {
    private int totalPage;
    private int totalCount;

    @JsonProperty("productInfos")
    private List<Item> productList;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Item {
      private String id;

      @JsonProperty("name")
      private String productName;
      private int price;

      private String thumbnailImage;
    }
  }

}

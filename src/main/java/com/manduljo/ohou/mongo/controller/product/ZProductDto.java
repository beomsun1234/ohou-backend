package com.manduljo.ohou.mongo.controller.product;

import com.fasterxml.jackson.annotation.JsonProperty;
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

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class FindProductByCategoryResponse {
    @JsonProperty("category")
    private List<CategoryItem> categoryList;

    @JsonProperty("product")
    private ProductPageInfo productPageInfo;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class CategoryItem {
      @JsonProperty("key")
      private String id;

      @JsonProperty("title")
      private String categoryName;

      @JsonProperty("parentId")
      private String parentCategoryId;

      @JsonProperty("children")
      private List<CategoryItem> categoryList;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class ProductPageInfo {
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

}

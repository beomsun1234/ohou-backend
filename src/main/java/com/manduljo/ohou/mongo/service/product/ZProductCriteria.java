package com.manduljo.ohou.mongo.service.product;

import com.manduljo.ohou.mongo.service.category.ZCategoryCriteria;
import lombok.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ZProductCriteria {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class GetProductDetailInfo {
    private String id;
    private String productName;
    private int price;
    private String thumbnailImage;
    private List<CoverImageItem> coverImageList;
    private String detailImage;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class CoverImageItem {
      private String main;
      private String left;
    }
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class FindProductBySearchTextCriteria {
    private String searchText;
    private Pageable pageable;
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class FindProductBySearchTextPageInfo {
    private int totalPage;
    private int totalCount;
    private List<Item> productList;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Item {
      private String id;
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
  public static class FindProductByCategoryCriteria {
    private String categoryId;
    private Pageable pageable;
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class FindProductByCategoryPageInfo {
    private int totalPage;
    private int totalCount;
    private List<Item> productList;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Item {
      private String id;
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
  public static class FindProductByCategoryResult {
    private ZCategoryCriteria.FindCategoryInfo categoryInfo;
    private ZProductCriteria.FindProductByCategoryPageInfo productPageInfo;
  }
}

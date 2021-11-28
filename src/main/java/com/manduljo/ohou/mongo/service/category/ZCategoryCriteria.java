package com.manduljo.ohou.mongo.service.category;

import lombok.*;

import java.util.List;

public class ZCategoryCriteria {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class FindCategoryInfo {
    private List<Item> categoryList;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Item {
      private String id;
      private String categoryName;
      private String parentCategoryId;
      private List<Item> categoryList;
    }
  }

}

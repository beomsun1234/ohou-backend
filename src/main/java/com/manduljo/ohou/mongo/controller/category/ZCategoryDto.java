package com.manduljo.ohou.mongo.controller.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

public class ZCategoryDto {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class FindCategoryResponse {
    @JsonProperty("category")
    private List<Item> categoryList;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Item {
      @JsonProperty("key")
      private String id;

      @JsonProperty("title")
      private String categoryName;

      @JsonProperty("parentId")
      private String parentCategoryId;

      @JsonProperty("children")
      private List<Item> categoryList;
    }

  }

}

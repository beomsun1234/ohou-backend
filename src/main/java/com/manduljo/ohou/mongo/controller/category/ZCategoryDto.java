package com.manduljo.ohou.mongo.controller.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.manduljo.ohou.domain.category.dto.CategoryInfo;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

public class ZCategoryDto {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class FindCategoryResponse {
    @JsonProperty("category")
    private List<CategoryItem> categoryList;

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

  }

}

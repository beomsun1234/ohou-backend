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
    private String id;
    private String categoryName;
    private String parentCategoryId;
    private List<ZCategoryCriteria.FindCategoryInfo> categoryInfoList;
  }

}

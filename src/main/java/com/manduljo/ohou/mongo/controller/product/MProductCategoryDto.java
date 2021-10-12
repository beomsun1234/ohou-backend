package com.manduljo.ohou.mongo.controller.product;

import lombok.*;

import java.util.List;

public class MProductCategoryDto {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class FindTreeResponse {
    private List<ProductCategoryTreeNode> productCategoryTreeNodeList;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class ProductCategoryTreeNode {
      private String id;
      private String productCategoryName;
      private List<ProductCategoryTreeNode> productCategoryTreeNodeList;
    }

  }

}

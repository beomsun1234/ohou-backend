package com.manduljo.ohou.mongo.service.product;

import lombok.*;

public class ZProductCriteria {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class FindProductInfo {
    private String id;
    private String productName;
    private String categoryId;
  }

}

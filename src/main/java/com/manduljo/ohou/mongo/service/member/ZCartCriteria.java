package com.manduljo.ohou.mongo.service.member;

import lombok.*;

import java.util.List;

public class ZCartCriteria {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class FindCartByMemberIdInfo {
    private int totalPrice;
    private List<Item> cartItemList;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Item {
      private String cartItemId;
      private String productId;
      private String productName;
      private String thumbnailImage;
      private int price;
      private int quantity;
      private int totalPrice;
    }
  }

}

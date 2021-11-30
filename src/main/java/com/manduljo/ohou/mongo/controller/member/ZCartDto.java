package com.manduljo.ohou.mongo.controller.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

public class ZCartDto {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class FindCartByMemberIdResponse {
    private int totalPrice;

    @JsonProperty("cartItems")
    private List<FindCartByMemberIdResponse.Item> cartItemList;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Item {
      private String cartItemId;
      private String productId;
      private String productName;

      @JsonProperty("productImage")
      private String thumbnailImage;
      private int price;
      private int quantity;
      private int totalPrice;
    }
  }

}

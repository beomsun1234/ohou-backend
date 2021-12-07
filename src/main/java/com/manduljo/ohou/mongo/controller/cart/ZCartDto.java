package com.manduljo.ohou.mongo.controller.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

public class ZCartDto {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class FindCartItemByMemberIdResponse {
    private int totalPrice;

    @JsonProperty("cartItems")
    private List<FindCartItemByMemberIdResponse.Item> cartItemList;

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

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class CreateCartItemRequest {
    private String productId;
    @JsonProperty("quantity")
    private int productQuantity;
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class UpdateCartItemProductQuantityRequest {
    private String cartItemId;
    @JsonProperty("quantity")
    private int productQuantity;
  }

}

package com.manduljo.ohou.mongo.controller.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

public class ZOrderDto {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class CreateCartOrderRequest {
    @JsonProperty("cartItemIds")
    private List<String> cartItemIdList;
    private String email;
    private String name;
    private String phone;
    private ShippingAddress shippingAddress;
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class CreateCartOrderResponse {
    private String orderId;
    private String memberId;
    private ShippingAddress shippingAddress;
    private int totalPrice;
    private List<Item> orderItemList;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Item {
      private String orderItemId;
      private String productId;
      private String productName;
      private int productPrice;
      private int productQuantity;
    }
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class CreateOrderRequest {
    @JsonProperty("productId")
    private List<String> productIdList;
    private String email;
    private String name;
    private String phone;
    private int price;
    private int quantity;
    private ShippingAddress shippingAddress;
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class CreateOrderResponse {
    private String orderId;
    private String memberId;
    private ShippingAddress shippingAddress;
    private int totalPrice;
    private List<Item> orderItemList;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Item {
      private String orderItemId;
      private String productId;
      private String productName;
      private int productPrice;
      private int productQuantity;
    }
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class ShippingAddress {
    private String zipCode;
    private String address;
    private String addressDetail;
  }

}

package com.manduljo.ohou.mongo.service.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

public class ZOrderCommand {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class CreateCartOrderCommand {
    private String memberId;
    @JsonProperty("cartItems")
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
  public static class CreateCartOrderInfo {
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
  public static class CreateOrderCommand {
    private String memberId;
    @JsonProperty("productId")
    private String productId;
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
  public static class CreateOrderInfo {
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

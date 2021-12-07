package com.manduljo.ohou.mongo.service.cart;

import lombok.*;

public class ZCartCommand {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class CreateCartItemCommand {
    private String memberId;
    private String productId;
    private int productQuantity;
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class UpdateCartItemProductQuantityCommand {
    private String memberId;
    private String cartItemId;
    private int productQuantity;
  }

}

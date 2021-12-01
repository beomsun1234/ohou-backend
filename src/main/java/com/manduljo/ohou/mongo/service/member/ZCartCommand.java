package com.manduljo.ohou.mongo.service.member;

import lombok.*;

public class ZCartCommand {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class AddCartItemCommand {
    private String memberId;
    private String productId;
    private int productQuantity;
  }

}

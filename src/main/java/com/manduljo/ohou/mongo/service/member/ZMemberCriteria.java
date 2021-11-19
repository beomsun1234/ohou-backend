package com.manduljo.ohou.mongo.service.member;

import lombok.*;

public class ZMemberCriteria {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class FindRequest {
    private String name;
  }
  
}

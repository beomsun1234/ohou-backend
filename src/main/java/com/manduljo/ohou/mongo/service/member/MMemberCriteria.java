package com.manduljo.ohou.mongo.service.member;

import lombok.*;

public class MMemberCriteria {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class FindRequest {
    private String name;
  }
  
}

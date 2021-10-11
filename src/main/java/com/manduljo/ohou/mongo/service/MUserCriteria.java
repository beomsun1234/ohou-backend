package com.manduljo.ohou.mongo.service;

import lombok.*;

public class MUserCriteria {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class FindRequest {
    private String name;
  }
  
}

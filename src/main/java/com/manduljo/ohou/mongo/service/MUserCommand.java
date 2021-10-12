package com.manduljo.ohou.mongo.service;

import lombok.*;

public class MUserCommand {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class SaveRequest {
    private String name;
    private int age;
  }
  
}

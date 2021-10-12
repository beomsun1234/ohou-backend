package com.manduljo.ohou.mongo.service.member;

import lombok.*;

public class MMemberCommand {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class SaveRequest {
    private String name;
    private String email;
  }
  
}
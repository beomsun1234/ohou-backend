package com.manduljo.ohou.mongo.service.member;

import lombok.*;

public class ZMemberCommand {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class CreateMemberCommand {
    private String name;
    private String email;
  }
  
}

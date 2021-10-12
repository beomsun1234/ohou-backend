package com.manduljo.ohou.mongo.controller.member;

import lombok.Getter;
import lombok.ToString;

public class MMemberDto {

  @Getter
  @ToString
  public static class FindRequest {
    private String name;
  }

  @Getter
  @ToString
  public static class FindResponse {
    private String name;
  }

  @Getter
  @ToString
  public static class SaveRequest {
    private String name;
    private String email;
  }

}

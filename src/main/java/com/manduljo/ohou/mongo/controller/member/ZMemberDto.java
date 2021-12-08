package com.manduljo.ohou.mongo.controller.member;

import com.manduljo.ohou.mongo.domain.member.ZGender;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

public class ZMemberDto {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class CreateMemberRequest {
    private String email;
    private String nickname;
    private String password;
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class CreateMemberResponse {
    private String id;
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class GetMemberDetailResponse {
    private String email;
    private String nickname;
    private String profileImage;
    private String introduce;
    private ZGender gender;
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class UpdateMemberRequest {
    private String nickname;
    private MultipartFile profileImage;
    private String introduce;
    private ZGender gender;
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class UpdateMemberResponse {
    private String email;
    private String nickname;
    private String profileImage;
    private String introduce;
    private ZGender gender;
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class UpdateMemberPasswordRequest {
    private String password;
    private String checkPassword;
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class UpdateMemberPasswordResponse {
    private String id;
  }

}

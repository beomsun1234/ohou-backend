package com.manduljo.ohou.mongo.service.member;

import com.manduljo.ohou.mongo.domain.member.ZGender;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

public class ZMemberCommand {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class CreateMemberCommand {
    private String email;
    private String nickname;
    private String password;
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class CreateMemberInfo {
    private String id;
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class GetMemberDetailInfo {
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
  public static class UpdateMemberCommand {
    private String id;
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
  public static class UpdateMemberInfo {
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
  public static class UpdateMemberPasswordCommand {
    private String id;
    private String password;
    private String checkPassword;
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  public static class UpdateMemberPasswordInfo {
    private String id;
  }

}

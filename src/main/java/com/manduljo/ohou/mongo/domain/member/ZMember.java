package com.manduljo.ohou.mongo.domain.member;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Document(collection = "members")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ZMember {

  @MongoId(FieldType.OBJECT_ID)
  private String id;
  private String email;
  private String nickname;
  private String password;
  private String profileImage;
  private String introduce;

  @Enumerated(EnumType.STRING)
  private ZGender gender;

  @Enumerated(EnumType.STRING)
  private ZRole role;

  @Enumerated(EnumType.STRING)
  private ZLoginType loginType;
  private List<ZCartItem> cartItemList;
  // private AuditMetadata auditMetadata;

}

package com.manduljo.ohou.mongo.domain.muser;

import com.manduljo.ohou.mongo.domain.AuditMetadata;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MUser {

  @MongoId(FieldType.OBJECT_ID)
  private String id;
  private String name;
  private int age;
  private AuditMetadata auditMetadata;

}

package com.manduljo.ohou.mongo.domain.category;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document(collection = "categories")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ZCategory {

  @MongoId(FieldType.OBJECT_ID)
  private String id;

  private String categoryName;

  @Field(targetType = FieldType.OBJECT_ID)
  private String parentCategoryId;

  private List<String> ancestorIdList;

  // private AuditMetadata auditMetadata;

}

package com.manduljo.ohou.mongo.domain.product;

import com.manduljo.ohou.mongo.domain.AuditMetadata;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document(collection = "product_categories")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MProductCategory {

  @MongoId(FieldType.OBJECT_ID)
  private String id;
  private String productCategoryName;
  @Field(targetType = FieldType.OBJECT_ID)
  private String parentProductCategoryId;
  private List<String> ancestorIdList;
  // private AuditMetadata auditMetadata;

}

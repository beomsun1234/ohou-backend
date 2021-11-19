package com.manduljo.ohou.mongo.domain.product;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "products")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ZProduct {

  @MongoId(FieldType.OBJECT_ID)
  private String id;

  private String productName;

  @Field(targetType = FieldType.OBJECT_ID)
  private String categoryId;

}

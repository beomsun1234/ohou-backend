package com.manduljo.ohou.mongo.domain.order;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ZOrderItem {

  @MongoId(FieldType.OBJECT_ID)
  private String id;

  @Field(targetType = FieldType.OBJECT_ID)
  private String productId;

  private String productName;

  private int productPrice;

  private int productQuantity;

}

package com.manduljo.ohou.mongo.domain.member;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ZCartItem {

  @MongoId(FieldType.OBJECT_ID)
  private String id;

  private String productId;

  private int productQuantity;

}

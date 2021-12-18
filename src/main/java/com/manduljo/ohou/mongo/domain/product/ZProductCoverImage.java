package com.manduljo.ohou.mongo.domain.product;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ZProductCoverImage {

  @MongoId(FieldType.OBJECT_ID)
  private String id;

  private String main;

  private String left;

}

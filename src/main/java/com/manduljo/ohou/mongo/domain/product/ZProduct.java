package com.manduljo.ohou.mongo.domain.product;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

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

  private int price;

  private String thumbnailImage;

  private List<String> productImageList;

  @Field(targetType = FieldType.OBJECT_ID)
  private String categoryId;

}

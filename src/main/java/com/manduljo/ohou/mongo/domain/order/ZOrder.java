package com.manduljo.ohou.mongo.domain.order;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document(collection = "orders")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ZOrder {

  @MongoId(FieldType.OBJECT_ID)
  private String id;

  @Field(targetType = FieldType.OBJECT_ID)
  private String memberId;

  private ZShippingAddress shippingAddress;

  private int totalPrice;

  private List<ZOrderItem> orderItemList;

}

package com.manduljo.ohou.mongo.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MUser {

  @MongoId
  private String id;
  private String name;
  private int age;

}

package com.manduljo.ohou.mongo.repository.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ZProductTemplateRepository {

  private final MongoTemplate mongoTemplate;

}

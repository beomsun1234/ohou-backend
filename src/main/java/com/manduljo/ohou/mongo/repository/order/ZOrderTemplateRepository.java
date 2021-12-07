package com.manduljo.ohou.mongo.repository.order;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ZOrderTemplateRepository {

  private final MongoTemplate mongoTemplate;


}

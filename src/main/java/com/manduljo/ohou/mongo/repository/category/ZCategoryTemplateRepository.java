package com.manduljo.ohou.mongo.repository.category;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ZCategoryTemplateRepository {

  private final MongoTemplate mongoTemplate;

}

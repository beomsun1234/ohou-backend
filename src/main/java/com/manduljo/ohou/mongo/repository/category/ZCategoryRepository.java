package com.manduljo.ohou.mongo.repository.category;

import com.manduljo.ohou.mongo.domain.category.ZCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ZCategoryRepository extends MongoRepository<ZCategory, String> {
}

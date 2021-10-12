package com.manduljo.ohou.mongo.repository.product;

import com.manduljo.ohou.mongo.domain.product.MProductCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MProductCategoryRepository extends MongoRepository<MProductCategory, String> {
}

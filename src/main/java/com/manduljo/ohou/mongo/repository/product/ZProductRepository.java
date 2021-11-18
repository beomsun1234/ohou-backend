package com.manduljo.ohou.mongo.repository.product;

import com.manduljo.ohou.mongo.domain.product.ZProduct;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ZProductRepository extends MongoRepository<ZProduct, String> {
}

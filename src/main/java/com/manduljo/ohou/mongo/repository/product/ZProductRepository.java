package com.manduljo.ohou.mongo.repository.product;

import com.manduljo.ohou.mongo.domain.product.ZProduct;
import com.manduljo.ohou.mongo.service.product.ZProductCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ZProductRepository extends MongoRepository<ZProduct, String> {
  Page<ZProduct> findByProductNameContains(String searchText, Pageable pageable);
}

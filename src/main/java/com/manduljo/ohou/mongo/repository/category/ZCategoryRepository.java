package com.manduljo.ohou.mongo.repository.category;

import com.manduljo.ohou.mongo.domain.category.ZCategory;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ZCategoryRepository extends MongoRepository<ZCategory, String> {

  List<ZCategory> findByAncestorIdList(ObjectId categoryId);

}

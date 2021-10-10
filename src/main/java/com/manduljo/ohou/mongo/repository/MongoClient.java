package com.manduljo.ohou.mongo.repository;

import com.manduljo.ohou.mongo.domain.MUser;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Component
@RequiredArgsConstructor
public class MongoClient {

  private final MongoTemplate mongoTemplate;

  public List<MUser> findAll() {
    return mongoTemplate.query(MUser.class).all();
  }

  public MUser findById(String id) {
    return mongoTemplate.findOne(Query.query(where("name").is("name1")), MUser.class, "user");
    // return mongoTemplate.findById(id, MUser.class, "user");
  }

}

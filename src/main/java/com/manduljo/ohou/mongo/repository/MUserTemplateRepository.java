package com.manduljo.ohou.mongo.repository;

import com.manduljo.ohou.mongo.domain.muser.MUser;
import com.manduljo.ohou.mongo.service.MUserCriteria;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Component
@RequiredArgsConstructor
public class MUserTemplateRepository {

  private final MongoTemplate mongoTemplate;

  public List<MUser> findAll(MUserCriteria.FindRequest criteria) {
    Query query = new Query();
    nameIs(query, criteria.getName());

    return query.getQueryObject().isEmpty() ?
        mongoTemplate.findAll(MUser.class) :
        mongoTemplate.find(query, MUser.class);
  }

  public MUser findById(String id) {
    return mongoTemplate.findById(id, MUser.class);
  }

  private void nameIs(Query query, String name) {
    if (StringUtils.hasText(name)) {
      query.addCriteria(where("name").is(name));
    }
  }

  public MUser save(MUser mUser) {
    return mongoTemplate.save(mUser);
  }

}

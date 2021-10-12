package com.manduljo.ohou.mongo.repository.member;

import com.manduljo.ohou.mongo.domain.member.MMember;
import com.manduljo.ohou.mongo.service.member.MMemberCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Component
@RequiredArgsConstructor
public class MMemberTemplateRepository {

  private final MongoTemplate mongoTemplate;

  public List<MMember> findAll(MMemberCriteria.FindRequest criteria) {
    Query query = new Query();
    nameIs(query, criteria.getName());

    return query.getQueryObject().isEmpty() ?
        mongoTemplate.findAll(MMember.class) :
        mongoTemplate.find(query, MMember.class);
  }

  public MMember findById(String id) {
    return mongoTemplate.findById(id, MMember.class);
  }

  private void nameIs(Query query, String name) {
    if (StringUtils.hasText(name)) {
      query.addCriteria(where("name").is(name));
    }
  }

  public MMember save(MMember mMember) {
    return mongoTemplate.save(mMember);
  }

}

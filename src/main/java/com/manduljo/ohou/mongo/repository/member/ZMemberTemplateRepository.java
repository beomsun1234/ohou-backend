package com.manduljo.ohou.mongo.repository.member;

import com.manduljo.ohou.mongo.domain.member.ZMember;
import com.manduljo.ohou.mongo.service.member.ZMemberCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Component
@RequiredArgsConstructor
public class ZMemberTemplateRepository {

  private final MongoTemplate mongoTemplate;

  public List<ZMember> findAll(ZMemberCriteria.FindRequest criteria) {
    Query query = new Query();
    nameIs(query, criteria.getName());

    return query.getQueryObject().isEmpty() ?
        mongoTemplate.findAll(ZMember.class) :
        mongoTemplate.find(query, ZMember.class);
  }

  public ZMember findById(String id) {
    return mongoTemplate.findById(id, ZMember.class);
  }

  private void nameIs(Query query, String name) {
    if (StringUtils.hasText(name)) {
      query.addCriteria(where("name").is(name));
    }
  }

  public ZMember save(ZMember member) {
    return mongoTemplate.save(member);
  }

}

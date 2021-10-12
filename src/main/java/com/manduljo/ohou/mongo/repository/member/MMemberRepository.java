package com.manduljo.ohou.mongo.repository.member;

import com.manduljo.ohou.mongo.domain.member.MMember;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MMemberRepository extends MongoRepository<MMember, String> {
}

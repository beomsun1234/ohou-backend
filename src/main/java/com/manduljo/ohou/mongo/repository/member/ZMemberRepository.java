package com.manduljo.ohou.mongo.repository.member;

import com.manduljo.ohou.mongo.domain.member.ZMember;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ZMemberRepository extends MongoRepository<ZMember, String> {
}

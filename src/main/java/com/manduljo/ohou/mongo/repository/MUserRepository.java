package com.manduljo.ohou.mongo.repository;

import com.manduljo.ohou.mongo.domain.muser.MUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MUserRepository extends MongoRepository<MUser, String> {
}

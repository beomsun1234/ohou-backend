package com.manduljo.ohou.mongo.repository;

import com.manduljo.ohou.mongo.domain.MUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MUserRepository extends MongoRepository<MUser, String> {

  Optional<MUser> findByNameIs(String name);

}

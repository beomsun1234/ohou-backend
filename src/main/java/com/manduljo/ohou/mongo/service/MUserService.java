package com.manduljo.ohou.mongo.service;

import com.manduljo.ohou.mongo.domain.MUser;
import com.manduljo.ohou.mongo.repository.MUserRepository;
import com.manduljo.ohou.mongo.repository.MongoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MUserService {

  private final MUserRepository mUserRepository;

  private final MongoClient mongoClient;

  public List<MUser> findAll() {
    return mongoClient.findAll();
  }

  public MUser findById(String id) {
    return mongoClient.findById(id);
    // return mUserRepository.findById(id).orElseThrow();
    // return mUserRepository.findByNameIs("name1").orElseThrow();
  }

}

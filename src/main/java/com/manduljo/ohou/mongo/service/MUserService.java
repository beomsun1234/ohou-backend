package com.manduljo.ohou.mongo.service;

import com.manduljo.ohou.mongo.domain.muser.MUser;
import com.manduljo.ohou.mongo.repository.MUserRepository;
import com.manduljo.ohou.mongo.repository.MUserTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MUserService {

  private final MUserRepository mUserRepository;

  private final MUserTemplateRepository mUserTemplateRepository;

  public List<MUser> findAll(MUserCriteria.FindRequest criteria) {
    return mUserTemplateRepository.findAll(criteria);
  }

  public MUser findById(String id) {
    return mUserRepository.findById(id).orElseThrow();
  }

  public String save(MUserCommand.SaveRequest request) {
    MUser mUser = mUserRepository.save(
        MUser.builder()
            .name(request.getName())
            .age(request.getAge())
            .build()
    );
    return mUser.getId();
  }
}

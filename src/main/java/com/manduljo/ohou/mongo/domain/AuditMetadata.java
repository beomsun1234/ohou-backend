package com.manduljo.ohou.mongo.domain;

import com.manduljo.ohou.mongo.domain.muser.MUser;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

public class AuditMetadata {

  @CreatedBy
  private MUser mUser;

  @CreatedDate
  private Instant createdDate;

}

package com.manduljo.ohou.mongo.domain;

import com.manduljo.ohou.mongo.domain.member.ZMember;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

public class AuditMetadata {

  @CreatedBy
  private ZMember member;

  @CreatedDate
  private Instant createdDate;

  // @CreatedDate
  // private LocalDateTime createdDate;
  //
  // @LastModifiedDate
  // private LocalDateTime modifiedDate;

}

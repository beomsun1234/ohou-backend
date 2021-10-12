package com.manduljo.ohou.mongo.domain;

import com.manduljo.ohou.mongo.domain.member.MMember;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.time.LocalDateTime;

public class AuditMetadata {

  @CreatedBy
  private MMember mMember;

  @CreatedDate
  private Instant createdDate;

  // @CreatedDate
  // private LocalDateTime createdDate;
  //
  // @LastModifiedDate
  // private LocalDateTime modifiedDate;

}

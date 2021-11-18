package com.manduljo.ohou.mongo.service.member;

import com.manduljo.ohou.mongo.domain.member.ZMember;
import com.manduljo.ohou.mongo.repository.member.ZMemberRepository;
import com.manduljo.ohou.mongo.repository.member.ZMemberTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ZMemberService {

  private final ZMemberRepository memberRepository;

  private final ZMemberTemplateRepository memberTemplateRepository;

  public List<ZMember> findAll(ZMemberCriteria.FindRequest criteria) {
    return memberTemplateRepository.findAll(criteria);
  }

  public ZMember findById(String id) {
    return memberRepository.findById(id).orElseThrow();
  }

  public String save(ZMemberCommand.SaveRequest request) {
    ZMember member = memberRepository.save(
        ZMember.builder()
            .name(request.getName())
            .email(request.getEmail())
            .build()
    );
    return member.getId();
  }

}

package com.manduljo.ohou.mongo.service.member;

import com.manduljo.ohou.mongo.domain.member.MMember;
import com.manduljo.ohou.mongo.repository.member.MMemberRepository;
import com.manduljo.ohou.mongo.repository.member.MMemberTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MMemberService {

  private final MMemberRepository memberRepository;

  private final MMemberTemplateRepository memberTemplateRepository;

  public List<MMember> findAll(MMemberCriteria.FindRequest criteria) {
    return memberTemplateRepository.findAll(criteria);
  }

  public MMember findById(String id) {
    return memberRepository.findById(id).orElseThrow();
  }

  public String save(MMemberCommand.SaveRequest request) {
    MMember user = memberRepository.save(
        MMember.builder()
            .name(request.getName())
            .email(request.getEmail())
            .build()
    );
    return user.getId();
  }

}

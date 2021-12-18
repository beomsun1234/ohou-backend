package com.manduljo.ohou.mongo.service.member;

import com.manduljo.ohou.mongo.repository.member.ZMemberRepository;
import com.manduljo.ohou.mongo.repository.member.ZMemberTemplateRepository;
import com.manduljo.ohou.mongo.util.ZImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ZAdminMemberService {

  private final ZMemberRepository memberRepository;
  private final ZMemberTemplateRepository memberTemplateRepository;

  private final PasswordEncoder passwordEncoder;
  private final ZImageUtil imageUtil;

}

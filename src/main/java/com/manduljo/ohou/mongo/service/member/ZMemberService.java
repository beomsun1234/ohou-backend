package com.manduljo.ohou.mongo.service.member;

import com.manduljo.ohou.mongo.domain.member.ZGender;
import com.manduljo.ohou.mongo.domain.member.ZLoginType;
import com.manduljo.ohou.mongo.domain.member.ZMember;
import com.manduljo.ohou.mongo.domain.member.ZRole;
import com.manduljo.ohou.mongo.repository.member.ZMemberRepository;
import com.manduljo.ohou.mongo.repository.member.ZMemberTemplateRepository;
import com.manduljo.ohou.mongo.util.ZImageUtil;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ZMemberService {

  private final ZMemberRepository memberRepository;
  private final ZMemberTemplateRepository memberTemplateRepository;

  private final PasswordEncoder passwordEncoder;
  private final ZImageUtil imageUtil;

  public ZMemberCommand.CreateMemberInfo createMember(ZMemberCommand.CreateMemberCommand command) {
    validateEmail(command.getEmail());
    ZMember member = toMember(command);
    ZMember savedMember = memberRepository.save(member);
    return toCreateMemberInfo(savedMember);
  }

  private void validateEmail(String email) {
    ZMember member = memberRepository.findByEmail(email);
    if (member != null) {
      throw new IllegalStateException("이미 존재하는 회원입니다.");
    }
  }

  private ZMember toMember(ZMemberCommand.CreateMemberCommand command) {
    return ZMember.builder()
        .email(command.getEmail())
        .nickname(command.getNickname())
        .password(passwordEncoder.encode(command.getPassword()))
        .gender(ZGender.MAN)
        .role(ZRole.ROLE_USER)
        .loginType(ZLoginType.OHOU)
        .build();
  }

  private ZMemberCommand.CreateMemberInfo toCreateMemberInfo(ZMember member) {
    return ZMemberCommand.CreateMemberInfo.builder()
        .id(member.getId())
        .build();
  }

  public ZMemberCommand.GetMemberDetailInfo getMemberDetail(String memberId) {
    ZMember member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));
    return toGetMemberDetailInfo(member);
  }

  private ZMemberCommand.GetMemberDetailInfo toGetMemberDetailInfo(ZMember member) {
    return ZMemberCommand.GetMemberDetailInfo.builder()
        .email(member.getEmail())
        .nickname(member.getNickname())
        .profileImage(member.getProfileImage())
        .introduce(member.getIntroduce())
        .gender(member.getGender())
        .build();
  }

  public ZMemberCommand.UpdateMemberInfo updateMember(ZMemberCommand.UpdateMemberCommand command) {
    String imagePath = imageUtil.generateImagePath(command.getId(), command.getProfileImage());
    ZMember member = memberTemplateRepository.updateMember(command, imagePath);
    if (member == null) {
      throw new IllegalArgumentException("해당 유저는 존재하지않습니다");
    }
    return toInfo(member);
  }

  private ZMemberCommand.UpdateMemberInfo toInfo(ZMember member) {
    return ZMemberCommand.UpdateMemberInfo.builder()
        .email(member.getEmail())
        .nickname(member.getNickname())
        .profileImage(member.getProfileImage())
        .introduce(member.getIntroduce())
        .gender(member.getGender())
        .build();
  }

  public ZMemberCommand.UpdateMemberPasswordInfo updateMemberPassword(ZMemberCommand.UpdateMemberPasswordCommand command) {
    validatePassword(command.getPassword(), command.getCheckPassword());
    UpdateResult updateResult = memberTemplateRepository.updateMemberPassword(command.getId(), passwordEncoder.encode(command.getPassword()));
    if (updateResult.getModifiedCount() == 0) {
      throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
    }
    return toInfo(command);
  }

  private void validatePassword(String password, String checkPassword) {
    if (!Objects.equals(password, checkPassword)) {
      throw new IllegalArgumentException("패스워드가 달라요");
    }
  }

  private ZMemberCommand.UpdateMemberPasswordInfo toInfo(ZMemberCommand.UpdateMemberPasswordCommand command) {
    return ZMemberCommand.UpdateMemberPasswordInfo.builder()
        .id(command.getId())
        .build();
  }
}

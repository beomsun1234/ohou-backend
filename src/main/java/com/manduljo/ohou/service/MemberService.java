package com.manduljo.ohou.service;

import com.manduljo.ohou.domain.member.dto.MemberJoinRequestDto;
import com.manduljo.ohou.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long join(MemberJoinRequestDto memberJoinRequestDto){
        validateEmail(memberJoinRequestDto.getEmail());
        return memberRepository.save(memberJoinRequestDto.toEntity(bCryptPasswordEncoder.encode(memberJoinRequestDto.getPassword()))).getId();
    }
    
    //이메일 검증
    private void validateEmail(String email){
        if(!memberRepository.findByEmail(email).isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}

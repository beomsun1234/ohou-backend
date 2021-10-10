package com.manduljo.ohou.service;
import com.manduljo.ohou.domain.member.Member;
import com.manduljo.ohou.domain.member.dto.MemberJoinRequestDto;
import com.manduljo.ohou.domain.member.dto.MemberUpdateRequestDto;
import com.manduljo.ohou.repository.member.MemberRepository;
import com.manduljo.ohou.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberService {
    private final ImageUtil imageUtil;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long join(MemberJoinRequestDto memberJoinRequestDto){
        validateEmail(memberJoinRequestDto.getEmail());
        return memberRepository.save(memberJoinRequestDto.toEntity(bCryptPasswordEncoder.encode(memberJoinRequestDto.getPassword()))).getId();
    }

    public Long updateInfo(Long userId, MemberUpdateRequestDto memberUpdateRequestDto) throws IOException {
        Member member = memberRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지않습니다"));
        if (memberUpdateRequestDto.getProfileImage()==null){
            return memberRepository.save(member.updateMyInfo(memberUpdateRequestDto.getNickname(), memberUpdateRequestDto.getGender(), member.getProfileImage(), memberUpdateRequestDto.getIntroduce())).getId();
        }

        if(!imageUtil.checkContentType(memberUpdateRequestDto.getProfileImage().getContentType())){
            throw new IOException("잘못된 컨텐츠입니다.");
        }
        String imagePath = imageUtil.genreateImagePath(member.getEmail(), memberUpdateRequestDto.getProfileImage());
        return memberRepository.save(member.updateMyInfo(memberUpdateRequestDto.getNickname(), memberUpdateRequestDto.getGender(), imagePath, memberUpdateRequestDto.getIntroduce())).getId();
    }

    //이메일 검증
    private void validateEmail(String email){
        if(!memberRepository.findByEmail(email).isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}

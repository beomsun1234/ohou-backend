package com.manduljo.ohou.service;
import com.manduljo.ohou.ApiCommonResponse;
import com.manduljo.ohou.domain.member.Member;
import com.manduljo.ohou.domain.member.dto.MemberInfo;
import com.manduljo.ohou.domain.member.dto.MemberJoinRequestDto;
import com.manduljo.ohou.domain.member.dto.MemberUpdateInfoRequestDto;
import com.manduljo.ohou.domain.member.dto.MemberUpdatePasswordDto;
import com.manduljo.ohou.repository.member.MemberRepository;
import com.manduljo.ohou.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberService {

    private final ImageUtil imageUtil;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 회원가입
     * @param memberJoinRequestDto
     * @return
     */
    @Transactional
    public ApiCommonResponse join(MemberJoinRequestDto memberJoinRequestDto){
        validateEmail(memberJoinRequestDto.getEmail());
        return ApiCommonResponse.builder()
                .message("회원가입 성공")
                .status(String.valueOf(HttpStatus.OK.value()))
                .data(memberRepository.save(memberJoinRequestDto.toEntity(bCryptPasswordEncoder.encode(memberJoinRequestDto.getPassword()))).getId())
                .build();
    }
    /**
     * 유저정보보
     * @param memberId
     * @return
     */
    @Cacheable(value = "member", key = "#memberId", unless = "#result == null ")
    @Transactional(readOnly = true)
    public MemberInfo getUserInfoById(Long memberId){
        MemberInfo memberInfo = MemberInfo.builder()
                .entity(memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다")))
                .build();
        return memberInfo;
    }
    /**
     * 유저정보 변경 및 프로필 업로드
     * @param userId
     * @param memberUpdateRequestDto
     * @return
     * @throws IOException
     */
    @CachePut(value = "member", key = "#userId", unless = "#result == null ")
    @Transactional
    public MemberInfo updateInfo(Long userId, MemberUpdateInfoRequestDto memberUpdateRequestDto) throws IOException {
        Member member = memberRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지않습니다"));
        if (memberUpdateRequestDto.getProfileImage()==null){
            return MemberInfo.builder().entity(memberRepository
                    .save(member.updateMyInfo(memberUpdateRequestDto.getNickname(), memberUpdateRequestDto.getGender(), member.getProfileImage(), memberUpdateRequestDto.getIntroduce())))
                    .build();
        }
        if(!imageUtil.checkContentType(memberUpdateRequestDto.getProfileImage().getContentType())){
            throw new IOException("잘못된 컨텐츠입니다.");
        }
        String imagePath = imageUtil.generateImagePath(member.getId().toString(), memberUpdateRequestDto.getProfileImage());
        return MemberInfo.builder().entity(memberRepository
                .save(member.updateMyInfo(memberUpdateRequestDto.getNickname(), memberUpdateRequestDto.getGender(), imagePath, memberUpdateRequestDto.getIntroduce())))
                .build();
    }

    /**
     * 패스워드 변경
     * @param userId
     * @param memberUpdatePasswordDto
     * @return
     */

    @Transactional
    public ApiCommonResponse updatePassword(Long userId, MemberUpdatePasswordDto memberUpdatePasswordDto){
        validatePassword(memberUpdatePasswordDto.getPassword(),memberUpdatePasswordDto.getCheckPassword());
        String password = bCryptPasswordEncoder.encode(memberUpdatePasswordDto.getPassword());
        Member member = memberRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        return ApiCommonResponse.builder()
                .message("패스워드 변경 성공")
                .status(String.valueOf(HttpStatus.OK.value()))
                .data(memberRepository.save(member.updatePassword(password)).getId())
                .build();
    }


    ///-------검증--------------
    //이메일 검증
    private void validateEmail(String email){
        if(!memberRepository.findByEmail(email).isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //패스워드 변경시 체크
    private void validatePassword(String password, String passwordck){
        if(!password.equals(passwordck)){
            throw new IllegalArgumentException("패스워드가 달라요");
        }
    }



}

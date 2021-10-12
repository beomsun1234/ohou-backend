package com.manduljo.ohou.service;
import com.manduljo.ohou.ApiCommonResponse;
import com.manduljo.ohou.domain.member.Member;
import com.manduljo.ohou.domain.member.dto.MemberInfo;
import com.manduljo.ohou.domain.member.dto.MemberJoinRequestDto;
import com.manduljo.ohou.domain.member.dto.MemberUpdateInfoRequestDto;
import com.manduljo.ohou.domain.member.dto.MemberUpdatePasswordDto;
import com.manduljo.ohou.oauth2.CustomUserDetails;
import com.manduljo.ohou.repository.member.MemberRepository;
import com.manduljo.ohou.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
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
     * 유저정보보기
     * @param authentication
     * @param memberId
     * @return
     */
    @Transactional(readOnly = true)
    public ApiCommonResponse getUserInfo(Authentication authentication, Long memberId){
        validateUser(authentication,memberId);
        MemberInfo memberInfo = MemberInfo.builder()
                .entity(memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다")))
                .build();
        return ApiCommonResponse.builder()
                .message("유저 정보")
                .status(String.valueOf(HttpStatus.OK.value()))
                .data(memberInfo)
                .build();
    }
    /**
     * 유저정보 변경 및 프로필 업로드
     * @param userId
     * @param memberUpdateRequestDto
     * @return
     * @throws IOException
     */
    @Transactional
    public Long updateInfo(Authentication authentication, Long userId, MemberUpdateInfoRequestDto memberUpdateRequestDto) throws IOException {
        validateUser(authentication,userId);
        Member member = memberRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지않습니다"));
        if (memberUpdateRequestDto.getProfileImage()==null){
            return memberRepository.save(member.updateMyInfo(memberUpdateRequestDto.getNickname(), memberUpdateRequestDto.getGender(), member.getProfileImage(), memberUpdateRequestDto.getIntroduce())).getId();
        }

        if(!imageUtil.checkContentType(memberUpdateRequestDto.getProfileImage().getContentType())){
            throw new IOException("잘못된 컨텐츠입니다.");
        }
        String imagePath = imageUtil.genreateImagePath(member.getId().toString(), memberUpdateRequestDto.getProfileImage());
        return memberRepository.save(member.updateMyInfo(memberUpdateRequestDto.getNickname(), memberUpdateRequestDto.getGender(), imagePath, memberUpdateRequestDto.getIntroduce())).getId();
    }

    /**
     * 패스워드 변경
     * @param userId
     * @param memberUpdatePasswordDto
     * @return
     */
    @Transactional
    public ApiCommonResponse updatePassword(Authentication authentication, Long userId, MemberUpdatePasswordDto memberUpdatePasswordDto){
        validateUser(authentication,userId);
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
    //접속한 유저 id와 해당 id가 자기 자신인지 체크
    private void validateUser(Authentication authentication, Long userId){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        if (!userDetails.getMember().getId().equals(userId)){
            throw new IllegalArgumentException("잘못된 요청입니다.");
        }
    }
    //패스워드 변경시 체크
    private void validatePassword(String password, String passwordck){
        if(!password.equals(passwordck)){
            throw new IllegalArgumentException("패스워드가 달라요");
        }
    }



}

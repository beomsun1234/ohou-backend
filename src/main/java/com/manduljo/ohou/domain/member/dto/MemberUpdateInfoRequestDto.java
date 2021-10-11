package com.manduljo.ohou.domain.member.dto;

import com.manduljo.ohou.domain.member.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Setter
@Getter
@NoArgsConstructor
public class MemberUpdateInfoRequestDto {
    private String nickname;
    private MultipartFile profileImage; //이미지
    private String introduce;
    private Gender gender;

    @Builder
    public MemberUpdateInfoRequestDto(String nickname, MultipartFile profileImage, String introduce, Gender gender){
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.introduce = introduce;
        this.gender = gender;
    }
}

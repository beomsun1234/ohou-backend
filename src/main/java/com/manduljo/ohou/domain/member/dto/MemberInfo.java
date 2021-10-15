package com.manduljo.ohou.domain.member.dto;

import com.manduljo.ohou.domain.member.Gender;
import com.manduljo.ohou.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class MemberInfo implements Serializable {
    private String email;
    private String nickname;
    private Gender gender;
    private String profieImage;
    private String introduce;

    @Builder
    public MemberInfo(Member entity){
        this.email=entity.getEmail();
        this.nickname=entity.getName();
        this.gender = entity.getGender();
        this.profieImage = entity.getProfileImage();
        this.introduce = entity.getIntroduce();
    }

}

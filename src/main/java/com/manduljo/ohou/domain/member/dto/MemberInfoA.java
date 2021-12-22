package com.manduljo.ohou.domain.member.dto;

import com.manduljo.ohou.domain.member.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberInfoA {
    private Long id;
    private String email;
    private String nickName;
    private String profileImage;
    private String introduce;
    private Gender gender;
    private Role role;
    private LoginType loginType;
    private StatusAt statusAt;
    @Builder
    public MemberInfoA(Member entity){
        this.id = entity.getId();
        this.email=entity.getEmail();
        this.nickName=entity.getName();
        this.gender = entity.getGender();
        this.profileImage = entity.getProfileImage();
        this.introduce = entity.getIntroduce();
        this.role = entity.getRole();
        this.loginType  = entity.getLoginType();
        this.statusAt = entity.getStatusAt();
    }

}

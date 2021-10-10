package com.manduljo.ohou.domain.member.dto;

import com.manduljo.ohou.domain.member.LoginType;
import com.manduljo.ohou.domain.member.Member;
import com.manduljo.ohou.domain.member.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberJoinRequestDto {
    private String email;
    private String password;
    private String nickname;

    @Builder
    public MemberJoinRequestDto(String email, String password, String nickname){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
    public Member toEntity(String pass){
        return Member.builder()
                .email(email)
                .name(nickname)
                .loginType(LoginType.OHOU)
                .role(Role.ROLE_USER)
                .password(pass)
                .build();
    }
}

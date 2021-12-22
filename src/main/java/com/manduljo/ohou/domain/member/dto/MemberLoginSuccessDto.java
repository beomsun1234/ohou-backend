package com.manduljo.ohou.domain.member.dto;

import com.manduljo.ohou.domain.member.Member;
import com.manduljo.ohou.domain.member.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberLoginSuccessDto {
    private Long id;
    private String access_token;
    private Role role;

    @Builder
    public MemberLoginSuccessDto(Member member,String access_token){
        this.id = member.getId();
        this.access_token = access_token;
        this.role = member.getRole();

    }
}

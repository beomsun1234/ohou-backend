package com.manduljo.ohou.domain.member.dto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUpdatePasswordDto {
    String password;
    String checkPassword;
    @Builder
    public MemberUpdatePasswordDto(String password,String checkPassword){
        this.password = password;
        this.checkPassword = checkPassword;
    }
}

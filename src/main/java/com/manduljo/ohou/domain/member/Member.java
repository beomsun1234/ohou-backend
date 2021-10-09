package com.manduljo.ohou.domain.member;
import com.manduljo.ohou.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @Builder
    public Member (String email, String name, String password, Role role, LoginType loginType){
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
        this.loginType = loginType;
    }

    public Member update(String email, String name){
        this.email = email;
        this.name=name;
        return this;
    }

}

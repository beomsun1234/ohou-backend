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

    private String profileImage;

    private String introduce;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;




    @Builder
    public Member (String email, String name, String password, Role role, LoginType loginType,String profileImage, Gender gender, String introduce){
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
        this.loginType = loginType;
        this.profileImage = profileImage;
        this.gender =  gender;
        this.introduce = introduce;
    }

    public Member updateEmail(String email){
        this.email = email;
        return this;
    }


    public Member updateMyInfo(String name, Gender gender, String profileImage,String introduce){
        this.name=name;
        this.gender = gender;
        this.profileImage = profileImage;
        this.introduce = introduce;
        return this;
    }

}

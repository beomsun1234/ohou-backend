package com.manduljo.ohou.oauth2;
import com.manduljo.ohou.domain.member.LoginType;
import com.manduljo.ohou.domain.member.Member;
import com.manduljo.ohou.domain.member.Role;
import lombok.Builder;
import lombok.Getter;
import java.util.Map;

@Getter
public class OAuth2Attributes {
    private Map<String, Object> attributes;
    private String userNameAttributeName;
    private String name;
    private String email;
    private LoginType loginType;
    private String profileImage;
    @Builder
    public OAuth2Attributes(Map<String, Object> attributes,
                            String userNameAttributeName,
                            String name,
                            String email,
                            LoginType loginType,
                            String profileImage
                            ) {
        this.attributes = attributes;
        this.userNameAttributeName = userNameAttributeName;
        this.name = name;
        this.email = email;
        this.loginType = loginType;
        this.profileImage = profileImage;
    }

    public static OAuth2Attributes of(String registrationId,
                                      String userNameAttributeName,
                                      Map<String, Object> attributes){

        if("kakao".equals(registrationId)){
            return ofKakao(userNameAttributeName, attributes);
        }
        if("naver".equals(registrationId)){
            return ofNaver(userNameAttributeName, attributes);
        }
        return ofGoogle(userNameAttributeName,attributes);
    }

    private static OAuth2Attributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuth2Attributes.builder()
                .email((String)attributes.get("email"))
                .name((String)attributes.get("name"))
                .attributes(attributes)
                .userNameAttributeName(userNameAttributeName)
                .loginType(LoginType.GOOGLE)
                .build();
    }
    private static OAuth2Attributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        // kakao는 kakao_account에 유저정보가 있다. - email만 존재
        Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
        // kakao_account안에 또 profile이라는 객체에 유저 이름이 있다. ->  nickname(이름)
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");
        return OAuth2Attributes.builder()
                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .attributes(attributes)
                .userNameAttributeName(userNameAttributeName)
                .loginType(LoginType.KAKAO)
                .build();
    }

    private static OAuth2Attributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        //네이버는 response라는 객체안에 email과 이름이있다.
        Map<String, Object> naverUser = (Map<String, Object>) attributes.get("response");
        return OAuth2Attributes.builder()
                .email((String)naverUser.get("email"))
                .name((String)naverUser.get("name"))
                .attributes(attributes)
                .userNameAttributeName(userNameAttributeName)
                .loginType(LoginType.NAVER)
                .build();
    }

    public Member toEntity(){
        return Member.builder()
                .email(email)
                .name(name)
                .loginType(loginType)
                .role(Role.ROLE_USER)
                .profileImage(profileImage)
                .build();
    }
}

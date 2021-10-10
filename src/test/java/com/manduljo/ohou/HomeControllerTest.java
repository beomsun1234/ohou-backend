package com.manduljo.ohou;

import com.manduljo.ohou.domain.member.LoginType;
import com.manduljo.ohou.domain.member.Member;
import com.manduljo.ohou.domain.member.Role;
import com.manduljo.ohou.repository.member.MemberRepository;
import com.manduljo.ohou.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static java.lang.String.format;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HomeControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private MemberRepository memberRepository;

    private String token;
    private RestTemplate restTemplate = new RestTemplate();

    private URI uri(String path) throws URISyntaxException {
        return new URI(format("http://localhost:%d%s", port,path));
    }

    @Transactional
    @BeforeEach
    @Test
    void setUp(){
        Member member = memberRepository.save(Member.builder().name("test").role(Role.ROLE_USER).email("bvbb").loginType(LoginType.OHOU).build());
        token = jwtUtil.generateToken(member.getEmail(),member.getName());
    }

    @Test
    @DisplayName("로그인완료후 받은 토큰으로 /api/test 경로로 들어올수있다")
    void test() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer "+token);
        HttpEntity entity = new HttpEntity("",httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri("/api/test"), HttpMethod.GET, entity, String.class);
    }

}
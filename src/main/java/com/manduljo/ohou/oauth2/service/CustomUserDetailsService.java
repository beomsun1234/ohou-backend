package com.manduljo.ohou.oauth2.service;

import com.manduljo.ohou.oauth2.CustomUserDetails;
import com.manduljo.ohou.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;


    @Override
    @Cacheable(value = "test", key = "#email", condition = "#result == null ")
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("loadUserByUsername");
        return new CustomUserDetails(memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("이메일이 없습니다")));
    }

}

package com.manduljo.ohou.service;

import com.manduljo.ohou.domain.member.Member;
import com.manduljo.ohou.domain.member.dto.MemberInfoA;
import com.manduljo.ohou.domain.product.dto.ProductInfo;
import com.manduljo.ohou.repository.member.MemberRepository;
import com.manduljo.ohou.repository.product.ProductQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final ProductQueryRepository productQueryRepository;
    private final MemberRepository memberRepository;
    /**
     * 상품 검색
     * @param searchText
     * @return
     */
    @Transactional(readOnly = true)
    public List<ProductInfo> findByProductName(String searchText){
        return productQueryRepository.findByProductName(searchText)
                .stream()
                .map(product -> ProductInfo.builder().product(product).build())
                .collect(Collectors.toList());
    }

    /**
     * 유저 전체조회
     * @return
     */
    @Transactional(readOnly = true)
    public List<MemberInfoA> findAll(){
        return memberRepository.findAll().stream().map(member -> MemberInfoA.builder().entity(member).build()).collect(Collectors.toList());
    }

    /**
     * 유저 이메일 조회
     * @param email
     * @return
     */
    @Transactional(readOnly = true)
    public MemberInfoA findEmail(String email){
        return MemberInfoA.builder().entity(memberRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("없는 사용자 입니다"))).build();
    }

    /**
     * 유저 상세 보기
     */
    @Transactional(readOnly = true)
    public MemberInfoA findById(Long id){
        return MemberInfoA.builder().entity(memberRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("없는 사용자 입니다"))).build();
    }

    /**
     * 유저 탈퇴시키기
     */
    @Transactional
    public void benUser(Long id){
        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("없는 사용자 입니다"));
        member.benUser();
        memberRepository.save(member);
    }

}

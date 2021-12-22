package com.manduljo.ohou.service;

import com.manduljo.ohou.domain.member.Member;
import com.manduljo.ohou.domain.member.dto.MemberInfoA;
import com.manduljo.ohou.domain.product.Product;
import com.manduljo.ohou.domain.product.dto.ProductInfo;
import com.manduljo.ohou.repository.member.MemberRepository;
import com.manduljo.ohou.repository.product.ProductQueryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;


import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {
    @Mock
    private ProductQueryRepository productQueryRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private AdminService adminService;

    @Test
    @DisplayName("상품검색 상품이름으로만")
    void findByProductName(){
        //given
        String searchText = "테스트";
        List<Product> products = Arrays.asList(Product.builder().name("테스트상품1").id(1L).build(), Product.builder().name("테스트상품2").id(2L).build());
        given(productQueryRepository.findByProductName(searchText)).willReturn(products);
        //when
        List<ProductInfo> productInfos = adminService.findByProductName("테스트");
        //then
        Assertions.assertThat(productInfos.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("유저 전체조회")
    void findAll(){
        //given
        List<Member> members = Arrays.asList(
                Member.builder().name("park").id(1L).build(),
                Member.builder().name("park2").id(2L).build(),
                Member.builder().name("park3").id(3L).build(),
                Member.builder().name("park4").id(4L).build()
        );
        given(memberRepository.findAll()).willReturn(members);
        //then
        List<MemberInfoA> memberInfos = adminService.findAll();
        //then
        Assertions.assertThat(memberInfos.size()).isEqualTo(4);
    }
    @Test
    @DisplayName("유저 이메일조회")
    void findEmail(){
        //given
        String searchText = "test1234";
        Member member = Member.builder().email("test1234").id(1L).build();
        given(memberRepository.findByEmail(searchText)).willReturn(java.util.Optional.ofNullable(member));
        //when
        MemberInfoA memberInfoA = adminService.findEmail(searchText);
        //then
        Assertions.assertThat(memberInfoA.getEmail()).isEqualTo(searchText);


    }


}
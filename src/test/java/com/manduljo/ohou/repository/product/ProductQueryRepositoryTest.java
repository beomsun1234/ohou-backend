package com.manduljo.ohou.repository.product;

import com.manduljo.ohou.domain.product.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductQueryRepositoryTest {

    @Autowired
    private ProductQueryRepository productQueryRepository;

    @DisplayName("메인페이지에 보여주기위해 부모카테고리 아이디를 가지고있는 모든 상품조회")
    @Test
    void test01(){
        List<Product> products = productQueryRepository.findByParentId(1L);
        //then
        System.out.println(products.get(0).getName());
        Assertions.assertThat(products.size()).isEqualTo(2);
        Assertions.assertThat(products.get(0).getName()).isEqualTo("원목소파");
    }

    @DisplayName("세부 카테고리에 해당하는 상품 조회")
    @Test
    void test02(){
        List<Product> products = productQueryRepository.findByCategoryId(4L);
        //then
        System.out.println(products.get(0).getName());
        Assertions.assertThat(products.size()).isEqualTo(1);
        Assertions.assertThat(products.get(0).getName()).isEqualTo("원목소파");
    }
    @DisplayName("동적쿼리, 상품이름 or 카테고리 이름")
    @Test
    void test03(){
        String searchText = "침대";
        List<Product> products = productQueryRepository.findByProdocutNameOrCategoryNameOrParentCategoryNameContaining(searchText);
        //then
        Assertions.assertThat(products.size()).isEqualTo(4);
    }
    @DisplayName("동적쿼리, 부모카테고리 검색")
    @Test
    void test04(){
        String searchText = "가구";
        List<Product> products = productQueryRepository.findByProdocutNameOrCategoryNameOrParentCategoryNameContaining(searchText);
        //then
        Assertions.assertThat(products.size()).isEqualTo(5);
    }
}
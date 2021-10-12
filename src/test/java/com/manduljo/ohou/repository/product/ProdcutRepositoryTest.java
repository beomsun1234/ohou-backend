package com.manduljo.ohou.repository.product;

import com.manduljo.ohou.domain.category.ProductCategory;
import com.manduljo.ohou.domain.product.Product;
import com.manduljo.ohou.repository.category.ProductCategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProdcutRepositoryTest {

    @MockBean
    private ProdcutRepository prodcutRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;


     ProductCategory setUpCategory(){
        //given
        //////카테고리설정
        ProductCategory categoryParent = ProductCategory.builder().name("가구").build();
        ProductCategory categoryParent2 = ProductCategory.builder().name("패브릭").build();
        ProductCategory savedParent = productCategoryRepository.save(categoryParent);
        ProductCategory savedParent2 = productCategoryRepository.save(categoryParent2);
        ////////자식생성
        ProductCategory subCategory = ProductCategory.builder().name("소파").parentCategory(savedParent).build();
        return productCategoryRepository.save(subCategory);
        ///-----------------
    }

    @DisplayName("crud test")
    @Test
    void save(){
        //given
        Product product = Product.builder().name("이단소파").price("10000").productCategory(setUpCategory()).build();
        //when
        Product savedProduct = prodcutRepository.save(product);
        //then
        Assertions.assertThat(savedProduct.getName()).isEqualTo(product.getName());
    }


//    @AfterEach
//    void clear(){
//        prodcutRepository.deleteAll();
//        productCategoryRepository.deleteAll();
//    }

}
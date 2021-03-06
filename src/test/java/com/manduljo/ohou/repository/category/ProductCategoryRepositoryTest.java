package com.manduljo.ohou.repository.category;

import com.manduljo.ohou.domain.category.ProductCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Test
    @DisplayName("기본적인 crud test")
    void save() {
        //given
        ProductCategory category = ProductCategory.builder().id("0").name("가구").build();
        productCategoryRepository.save(category);
        //when
        ProductCategory savedCategory = productCategoryRepository.save(category);
        //then
        Assertions.assertThat(savedCategory.getName()).isEqualTo(category.getName());
    }

    @Test
    @DisplayName("기본적인 crud test")
    void 조회(){
        //given
        ProductCategory category = ProductCategory.builder().id("0").name("가구").build();
        productCategoryRepository.save(category);
        ProductCategory category2 = ProductCategory.builder().id("1").name("페브릭").build();
        productCategoryRepository.save(category2);
        //then
        List<ProductCategory> categories = productCategoryRepository.findAll();
        //given
        Assertions.assertThat(categories.size()).isEqualTo(2);
    }

}
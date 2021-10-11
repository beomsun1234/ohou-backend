package com.manduljo.ohou.repository.category;

import com.manduljo.ohou.domain.category.ProductCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductQueryRepositoryTest {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ProductCategoryQueryRepository productCategoryQueryRepository;

    @Test
    @DisplayName("findAll test")
    void test(){
        ProductCategory category = ProductCategory.builder().name("가구").build();
        ProductCategory category2 = ProductCategory.builder().name("패브릭").build();
        productCategoryRepository.save(category);
        productCategoryRepository.save(category2);
        //then
        List<ProductCategory> categories = productCategoryQueryRepository.findAll();
        //given
        Assertions.assertThat(categories.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("parentId를 통해 서브 카테고리찾기 test")
    void test2() {
        //given
        ProductCategory categoryParent = ProductCategory.builder().name("가구").build();
        ProductCategory categoryParent2 = ProductCategory.builder().name("패브릭").build();
        ProductCategory savedParent = productCategoryRepository.save(categoryParent);
        ProductCategory savedParent2 = productCategoryRepository.save(categoryParent2);
        ///자식생성
        ProductCategory subCategory = ProductCategory.builder().name("소파").parentCategory(savedParent).build();
        productCategoryRepository.save(subCategory);
        //when
        List<ProductCategory> subCategories = productCategoryQueryRepository.findCategoryByParentId(savedParent.getId());
        //then
        Assertions.assertThat(subCategories.size()).isEqualTo(1);
    }

    @AfterEach
    void clear(){
        productCategoryRepository.deleteAll();
    }
}
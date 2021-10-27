package com.manduljo.ohou.repository.category;

import com.manduljo.ohou.domain.category.ProductCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;


@DataJpaTest
class ProductCategoryQueryRepositoryTest {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Mock
    private ProductCategoryQueryRepository productCategoryQueryRepository;

    @Test
    @DisplayName("findAll test")
    void test(){
        ProductCategory category = ProductCategory.builder().id("0").name("가구").parentCategory(null).build();
        ProductCategory category2 = ProductCategory.builder().id("1").name("패브릭").parentCategory(null).build();
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
        ProductCategory categoryParent = ProductCategory.builder().id("0").name("가구").build();
        ProductCategory categoryParent2 = ProductCategory.builder().id("1").name("패브릭").build();
        ProductCategory savedParent = productCategoryRepository.save(categoryParent);
        ProductCategory savedParent2 = productCategoryRepository.save(categoryParent2);
        ///자식생성
        ProductCategory subCategory = ProductCategory.builder().id("0_1").name("소파").parentCategory(savedParent).build();
        productCategoryRepository.save(subCategory);
        //when
        List<ProductCategory> subCategories = productCategoryQueryRepository.findCategoryByParentId(savedParent.getId());
        //then
        Assertions.assertThat(subCategories.size()).isEqualTo(1);
    }
    @Test
    @DisplayName("메인카테고리 select")
    void test3(){
        //given
        ProductCategory categoryParent = ProductCategory.builder().id("0").name("가구").build();
        ProductCategory categoryParent2 = ProductCategory.builder().id("1").name("패브릭").build();
        ProductCategory savedParent = productCategoryRepository.save(categoryParent);
        ProductCategory savedParent2 = productCategoryRepository.save(categoryParent2);
        ///자식생성
        ProductCategory subCategory = ProductCategory.builder().id("0_1").name("소파").parentCategory(savedParent).build();
        productCategoryRepository.save(subCategory);
        //when
        List<ProductCategory> mainCategory = productCategoryQueryRepository.findMainCategory();
        //then
        Assertions.assertThat(mainCategory.size()).isEqualTo(2);
    }

}
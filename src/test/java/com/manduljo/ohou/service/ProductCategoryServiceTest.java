package com.manduljo.ohou.service;

import com.manduljo.ohou.domain.category.ProductCategory;
import com.manduljo.ohou.domain.category.dto.CategoryCreateRequestDto;
import com.manduljo.ohou.domain.category.dto.CategoryInfo;
import com.manduljo.ohou.repository.category.ProductCategoryQueryRepository;
import com.manduljo.ohou.repository.category.ProductCategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProductCategoryServiceTest {

    @Mock
    private ProductCategoryQueryRepository productCategoryQueryRepository;
    @Mock
    private ProductCategoryRepository productCategoryRepository;
    @InjectMocks
    private ProductCategoryService productCategoryService;


    @Test
    @DisplayName("카테고리 조회(트리형식)")
    void test_findAllCategory(){
        //given
        //부모 카테고리
        ProductCategory pCategory = ProductCategory.builder().id("0").name("가구").build();
        ProductCategory pCategory2 = ProductCategory.builder().id("1").name("페브릭").build();
        //자식 카테고리
        ProductCategory subCategory = ProductCategory.builder().id("0_1").name("소파").parentCategory(pCategory).build();
        ProductCategory subCategory2 = ProductCategory.builder().id("1_1").name("거실페브릭").parentCategory(pCategory2).build();
        List<ProductCategory> productCategoryList = new ArrayList<>();
        productCategoryList.add(pCategory);
        productCategoryList.add(pCategory2);
        productCategoryList.add(subCategory);
        productCategoryList.add(subCategory2);
        //when
        when(productCategoryQueryRepository.findAll()).thenReturn(productCategoryList);
        List<CategoryInfo> findAllCategory = productCategoryService.findAllCategory();
        //then
        Assertions.assertThat(findAllCategory.size()).isEqualTo(2);
        Assertions.assertThat(findAllCategory.get(0).getChildren().get(0).getTitle()).isEqualTo("소파");
        Assertions.assertThat(findAllCategory.get(1).getChildren().get(0).getTitle()).isEqualTo("거실페브릭");
    }

    @Test
    @DisplayName("카테고리 조회시 null 값일 에러가 발생하지 않는다.")
    void test2_findAllCategory(){
        //given
        given(productCategoryQueryRepository.findAll()).willReturn(new ArrayList<>());
        //when
        List<CategoryInfo> findAllCategory = productCategoryService.findAllCategory();
        //then
        Assertions.assertThat(findAllCategory.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("루트 카테고리 생성")
    void test_createCategory(){
        //given
        CategoryCreateRequestDto categoryCreateRequestDto = CategoryCreateRequestDto.builder().id("0").name("가구").build();
        ProductCategory category = categoryCreateRequestDto.toEntity(null);
        System.out.println(category.getId());
        //when
        when(productCategoryRepository.save(any(ProductCategory.class))).thenReturn(category);
        String categoryId = productCategoryService.createCategory(categoryCreateRequestDto);
        Assertions.assertThat(categoryId).isEqualTo("0");
    }

    @Test
    @DisplayName("자식 카테고리 생성")
    void test2_createCategory(){
        //given
        ProductCategory pCategory = ProductCategory.builder().id("0").name("가구").build();
        CategoryCreateRequestDto categoryCreateRequestDto = CategoryCreateRequestDto.builder().id("0_1").name("소파").build();
        ProductCategory category = categoryCreateRequestDto.toEntity(pCategory);
        //when
        when(productCategoryRepository.save(any(ProductCategory.class))).thenReturn(category);
        String categoryId = productCategoryService.createCategory(categoryCreateRequestDto);
        //then
        Assertions.assertThat(categoryId).isEqualTo("0_1");
    }


}
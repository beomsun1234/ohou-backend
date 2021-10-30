package com.manduljo.ohou.service;

import com.manduljo.ohou.ApiCommonResponse;
import com.manduljo.ohou.domain.category.ProductCategory;
import com.manduljo.ohou.domain.product.Product;
import com.manduljo.ohou.domain.product.dto.ProductDetail;
import com.manduljo.ohou.domain.product.dto.ProductInfo;
import com.manduljo.ohou.repository.category.ProductCategoryQueryRepository;
import com.manduljo.ohou.repository.product.ProductQueryRepository;
import com.manduljo.ohou.repository.product.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductQueryRepository productQueryRepository;

    @InjectMocks
    private ProductService productService;


    @Test
    @DisplayName("상품상세 검색 by Id")
    void test_getProductDetailById(){
        //given
        Product product = Product.builder().id(1L).name("테스트").price(20000)
                .productCategory(ProductCategory.builder().id("0_1").parentCategory(ProductCategory.builder().id("0").build()).build()).build();
        given(productQueryRepository.findById(anyLong())).willReturn(java.util.Optional.ofNullable(product));
        //when
        ProductDetail productDetail = productService.getProductDetailById(1L);
        //then
        Assertions.assertThat(productDetail.getName()).isEqualTo("테스트");
        Assertions.assertThat(productDetail.getPrice()).isEqualTo(20000);
    }
    @Test
    @DisplayName("카테고리id별 검색")
    void test_findProductByCategoryId(){
        //given
        Pageable pageRequest = PageRequest.of(0, 5);
        List<Product> products = new ArrayList<>();
        Product product = Product.builder().id(1L).name("테스트").price(20000)
                .productCategory(ProductCategory.builder().id("0_1").parentCategory(ProductCategory.builder().id("0").build()).build()).build();
        Product product2 = Product.builder().id(2L).name("테스트2").price(30000)
                .productCategory(ProductCategory.builder().id("0_1").parentCategory(ProductCategory.builder().id("0").build()).build()).build();
        products.add(product);
        products.add(product2);
        given(productQueryRepository.findByCategoryId(any(),anyString())).willReturn(products);
        String fakeCategoryId = "0_1";
        //when
        List<ProductInfo> productInfos = productService.findProductByCategoryId(fakeCategoryId, pageRequest);
        //then
        Assertions.assertThat(productInfos.size()).isEqualTo(2);
        Assertions.assertThat(productInfos.get(0).getName()).isEqualTo("테스트");
    }

    @Test
    @DisplayName("상품 검색")
    void test_DynamicProductInfo(){
        //given
        List<Product> products = new ArrayList<>();
        Product product = Product.builder().id(1L).name("테스트").price(20000)
                .productCategory(ProductCategory.builder().id("0_1").parentCategory(ProductCategory.builder().id("0").build()).build()).build();
        Product product2 = Product.builder().id(2L).name("테스트2").price(30000)
                .productCategory(ProductCategory.builder().id("0_1").parentCategory(ProductCategory.builder().id("0").build()).build()).build();
        products.add(product);
        products.add(product2);
        String searchText = "테스트";
        given(productQueryRepository.findByProdocutNameOrCategoryNameOrParentCategoryNameContaining(searchText)).willReturn(products);
        //when
        List<ProductInfo> productInfos = productService.getDynamicProductInfo(searchText);
        //then
        Assertions.assertThat(productInfos.size()).isEqualTo(2);
        Assertions.assertThat(productInfos.get(0).getName()).isEqualTo("테스트");
    }







}
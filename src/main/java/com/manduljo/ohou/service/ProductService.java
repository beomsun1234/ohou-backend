package com.manduljo.ohou.service;

import com.manduljo.ohou.ApiCommonResponse;
import com.manduljo.ohou.domain.category.ProductCategory;
import com.manduljo.ohou.domain.product.Product;
import com.manduljo.ohou.domain.product.dto.ProductDetail;
import com.manduljo.ohou.domain.product.dto.ProductInfo;
import com.manduljo.ohou.repository.category.ProductCategoryQueryRepository;
import com.manduljo.ohou.repository.product.ProductRepository;
import com.manduljo.ohou.repository.product.ProductQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductQueryRepository productQueryRepository;
    private final ProductCategoryQueryRepository productCategoryQueryRepository;
    private final ProductRepository productRepository;
    /**
     * 상품 상세
     */
    @Transactional(readOnly = true)
    public ApiCommonResponse getProductDetailById(Long id){
        return ApiCommonResponse.builder()
                .status(String.valueOf(HttpStatus.OK.value()))
                .message("성공")
                .data(ProductDetail.builder()
                        .entity(productQueryRepository
                                .findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다.")))
                        .build())
                .build();
    }
    /**
     * 카테고리id별 검색
     * @param id
     * @return
     */
    @Cacheable(value = "products", key = "{#id,#pageable.pageNumber}", unless = "#result.size()<0")
    @Transactional(readOnly = true)
    public List<ProductInfo> findProductByCategoryId(String id, Pageable pageable){
        return productQueryRepository.findByCategoryId(pageable, id)
                .stream()
                .map(product -> ProductInfo.builder().product(product).build())
                .collect(Collectors.toList());
    }

    /**
     * 상품검색
     * @param searchText
     * @return
     */
    @Cacheable(value = "products", key = "#searchText", unless = "#result.size()<20")
    @Transactional(readOnly = true)
    public List<ProductInfo> getDynamicProductInfo(String searchText){
        return productQueryRepository.findByProdocutNameOrCategoryNameOrParentCategoryNameContaining(searchText)
                .stream()
                .map(product -> ProductInfo.builder().product(product).build())
                .collect(Collectors.toList());
    }


    public void createProduct(String id){
        //상품 등록은 서브카테고리만 가능함
        ProductCategory category = productCategoryQueryRepository.findById(id).orElseThrow(()-> new NoSuchElementException("없는 카테고리 입니다"));
        Product product = Product.builder().productCategory(category).price("10000").name("test").build();
        productRepository.save(product);
    }
}

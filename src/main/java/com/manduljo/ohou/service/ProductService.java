package com.manduljo.ohou.service;

import com.manduljo.ohou.ApiCommonResponse;
import com.manduljo.ohou.domain.product.Product;
import com.manduljo.ohou.domain.product.dto.ProductDetail;
import com.manduljo.ohou.domain.product.dto.ProductInfo;
import com.manduljo.ohou.repository.product.ProductQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductQueryRepository productQueryRepository;

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
     * 카테고리id
     * @param id
     * @return
     */
    @Cacheable(value = "products", key = "#id", unless = "#result.size()<20")
    @Transactional(readOnly = true)
    public List<ProductInfo> findProductByCategoryId(String id){
        return productQueryRepository.findByCategoryId(id)
                .stream()
                .map(product -> ProductInfo.builder().product(product).build())
                .collect(Collectors.toList());
    }

    @Cacheable(value = "products", key = "#searchText", unless = "#result.size()<20")
    @Transactional(readOnly = true)
    public List<ProductInfo> getDynamicProductInfo(String searchText){
        return productQueryRepository.findByProdocutNameOrCategoryNameOrParentCategoryNameContaining(searchText)
                .stream()
                .map(product -> ProductInfo.builder().product(product).build())
                .collect(Collectors.toList());
    }
}

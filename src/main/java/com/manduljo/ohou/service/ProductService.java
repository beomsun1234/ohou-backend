package com.manduljo.ohou.service;


import com.manduljo.ohou.domain.category.ProductCategory;
import com.manduljo.ohou.domain.product.Product;
import com.manduljo.ohou.domain.product.dto.ProductCreateDto;
import com.manduljo.ohou.domain.product.dto.ProductDetail;
import com.manduljo.ohou.domain.product.dto.ProductInfo;
import com.manduljo.ohou.domain.product.dto.ProductPageDto;
import com.manduljo.ohou.repository.category.ProductCategoryQueryRepository;
import com.manduljo.ohou.repository.product.ProductRepository;
import com.manduljo.ohou.repository.product.ProductQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


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
    public ProductDetail getProductDetailById(Long id){
        return ProductDetail.builder()
                .entity(productQueryRepository
                        .findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다.")))
                .build();
    }
    /**
     * 카테고리id별 검색
     * @param id
     * @return
     */
    @Cacheable(value = "products", key = "{#id,#pageable.pageNumber}", unless = "#result == null")
    @Transactional(readOnly = true)
    public ProductPageDto findProductByCategoryId(String id, Pageable pageable){
        Page<Product> products = productQueryRepository.findByCategoryId(pageable, id);

        List<ProductInfo> productInfos = products.getContent().stream()
                .map(product -> ProductInfo.builder().product(product).build())
                .collect(Collectors.toList());

        return ProductPageDto.builder().productInfoList(productInfos)
                .totalCount((int) products.getTotalElements())
                .totalPage(products.getTotalPages()).build();
    }

    /**
     * 상품검색
     * @param searchText
     * @return
     */
    @Cacheable(value = "products", key = "{#searchText, #pageable.pageNumber}", unless = "#result == null")
    @Transactional(readOnly = true)
    public ProductPageDto getDynamicProductInfo(Pageable pageable,String searchText){
        Page<Product> findProducts = productQueryRepository.findByProdocutNameOrCategoryNameOrParentCategoryNameContaining(pageable, searchText);

        List<ProductInfo> productInfos = findProducts.getContent()
                .stream()
                .map(product -> ProductInfo.builder().product(product).build())
                .collect(Collectors.toList());

        return ProductPageDto.builder().productInfoList(productInfos)
                .totalCount((int) findProducts.getTotalElements())
                .totalPage(findProducts.getTotalPages()).build();
    }


    /**
     * 상품등록
     * @param productCreateDto
     */
    @Transactional
    public void createProduct(ProductCreateDto productCreateDto){
        //상품 등록은 서브카테고리만 가능함
        ProductCategory category = productCategoryQueryRepository.findById(productCreateDto.getCategory_id()).orElseThrow(()-> new NoSuchElementException("없는 카테고리 입니다"));
        productRepository.save(productCreateDto.toEntity(category));
    }
}

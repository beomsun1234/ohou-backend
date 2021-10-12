package com.manduljo.ohou.service;

import com.manduljo.ohou.ApiCommonResponse;
import com.manduljo.ohou.domain.category.ProductCategory;
import com.manduljo.ohou.domain.category.dto.CategoryCreateRequestDto;
import com.manduljo.ohou.domain.category.dto.CategoryInfo;
import com.manduljo.ohou.repository.category.ProductCategoryQueryRepository;
import com.manduljo.ohou.repository.category.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {
    private final ProductCategoryQueryRepository productCategoryQueryRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Transactional(readOnly = true)
    @Cacheable(value = "category" , key = "#id")
    public List<CategoryInfo> findCategoryById(String id){
        return productCategoryQueryRepository.findCategoryByParentId(id)
                .stream()
                .map(productCategory -> CategoryInfo.builder().entity(productCategory).build())
                .collect(Collectors.toList());
    }
    //카테고리 생성

    @Transactional
    @CacheEvict(value = "category", key = "#categoryCreateRequestDto.parentId")
    public ApiCommonResponse createCategory(@RequestBody CategoryCreateRequestDto categoryCreateRequestDto){
        ProductCategory pCategory = productCategoryQueryRepository.findById(categoryCreateRequestDto.getParentId()).orElseThrow();
        return ApiCommonResponse.builder()
                .message("저장성공")
                .status(String.valueOf(HttpStatus.OK.value()))
                .data(productCategoryRepository.save(categoryCreateRequestDto.toEntity(pCategory)).getId())
                .build();
    }
}

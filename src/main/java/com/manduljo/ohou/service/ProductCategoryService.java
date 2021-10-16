package com.manduljo.ohou.service;

import com.manduljo.ohou.ApiCommonResponse;
import com.manduljo.ohou.domain.category.ProductCategory;
import com.manduljo.ohou.domain.category.dto.CategoryCreateRequestDto;
import com.manduljo.ohou.domain.category.dto.CategoryInfo;
import com.manduljo.ohou.repository.category.ProductCategoryQueryRepository;
import com.manduljo.ohou.repository.category.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductCategoryService {
    private final ProductCategoryQueryRepository productCategoryQueryRepository;
    private final ProductCategoryRepository productCategoryRepository;


    @Transactional(readOnly = true)
    @Cacheable(value = "allCategory", key = "1")
    public List<CategoryInfo> findAllCategory(){
        Map<String, List<CategoryInfo>> collect = productCategoryQueryRepository
                .findAll()
                .stream()
                .map(category -> CategoryInfo.builder().entity(category).build())
                .collect(Collectors.groupingBy(CategoryInfo::getParentId));
        if (collect.isEmpty()){
            return Collections.singletonList(CategoryInfo.builder().entity(null).build());
        }
        //루트인것을 가져와서 루트에 자식들을 셋팅해준다.(이방식으로 할 경우 레벨이 늘어날경우 유연하게 대처하기 힘들다 나중에 고려하자)
        List<CategoryInfo> sub = collect.get("root");
        sub.forEach(categoryInfo -> categoryInfo.setChild(collect.get(categoryInfo.getId())));
        return sub;
    }


    //카테고리 생성
    @Transactional
    @CacheEvict(value = "allCategory", allEntries = true, condition = "#result!=null")
    public String createCategory(@RequestBody CategoryCreateRequestDto categoryCreateRequestDto){
        if(productCategoryQueryRepository
                .findByIdOrName(categoryCreateRequestDto.getId(),categoryCreateRequestDto.getName()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 카테고리 입니다.");
        }
        log.info("----------------------------------------------------");
        if(categoryCreateRequestDto.getParentId()!= null){
            ProductCategory parentCategory = productCategoryQueryRepository
                    .findById(categoryCreateRequestDto.getParentId())
                    .orElseThrow(()->new NoSuchElementException("저장하시려는 카테고리는의 루트는 없는 카테고리 입니다"));

            log.info("부모id={}",parentCategory.getId());
            ProductCategory category = categoryCreateRequestDto.toEntity(parentCategory);

            log.info("id={}",category.getId());
            return productCategoryRepository.save(category).getId();
        }
        return productCategoryRepository.save(categoryCreateRequestDto.toEntity(null)).getId();
    }
}

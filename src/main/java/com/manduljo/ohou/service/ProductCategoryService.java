package com.manduljo.ohou.service;

import com.manduljo.ohou.domain.category.dto.CategoryInfo;
import com.manduljo.ohou.repository.category.ProductCategoryQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {
    private final ProductCategoryQueryRepository productCategoryQueryRepository;

    public List<CategoryInfo> findCategoryById(String id){
        return productCategoryQueryRepository.findCategoryByParentId(id)
                .stream()
                .map(productCategory -> CategoryInfo.builder().entity(productCategory).build())
                .collect(Collectors.toList());
    }
}

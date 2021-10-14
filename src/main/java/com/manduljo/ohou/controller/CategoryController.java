package com.manduljo.ohou.controller;

import com.manduljo.ohou.ApiCommonResponse;

import com.manduljo.ohou.domain.category.dto.CategoryCreateRequestDto;

import com.manduljo.ohou.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequestMapping("api")
@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final ProductCategoryService productCategoryService;

    /**
     * 카테고리저장
     * @param categoryCreateRequestDto
     * @return
     */
    @PostMapping("category")
    public ApiCommonResponse createCategory(@RequestBody CategoryCreateRequestDto categoryCreateRequestDto){
        return productCategoryService.createCategory(categoryCreateRequestDto);
    }

    @GetMapping("category")
    public ApiCommonResponse findAllTreeCategory(){
        return productCategoryService.findAllCategory();
    }
}

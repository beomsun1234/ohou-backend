package com.manduljo.ohou.controller;

import com.manduljo.ohou.ApiCommonResponse;

import com.manduljo.ohou.domain.category.dto.CategoryCreateRequestDto;

import com.manduljo.ohou.domain.category.dto.CategoryInfo;
import com.manduljo.ohou.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    public ResponseEntity<ApiCommonResponse> createCategory(@RequestBody CategoryCreateRequestDto categoryCreateRequestDto){
        return new ResponseEntity<>(ApiCommonResponse.builder()
                .status(String.valueOf(HttpStatus.OK.value()))
                .message("저장완료")
                .data(productCategoryService.createCategory(categoryCreateRequestDto))
                .build(),HttpStatus.OK);
    }

    @GetMapping("category")
    public ResponseEntity<ApiCommonResponse> findAllTreeCategory(){
        Map<String, List<CategoryInfo>> categoryInfoMap = new HashMap<>();
        categoryInfoMap.put("category",productCategoryService.findAllCategory());
        return new ResponseEntity<>(ApiCommonResponse.builder()
                .status(String.valueOf(HttpStatus.OK.value()))
                .message("카테고리 조회 성공")
                .data(categoryInfoMap)
                .build(), HttpStatus.OK);
    }
}

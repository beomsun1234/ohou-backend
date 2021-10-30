package com.manduljo.ohou.controller;

import com.manduljo.ohou.ApiCommonResponse;
import com.manduljo.ohou.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    /**
     * 상품 상세
     */
    @GetMapping("products/{id}")
    public ApiCommonResponse getProductDetail(@PathVariable Long id){
        return ApiCommonResponse.builder()
                .status(String.valueOf(HttpStatus.OK.value()))
                .message("성공")
                .data(productService.getProductDetailById(id))
                .build();
    }
    /**
     * 상품 검색
     * @param searchText
     * @return
     */
    @GetMapping("products/search")
    public ApiCommonResponse getDynamicProductInfo(@RequestParam(name = "search", required = false, defaultValue = "") String searchText) {
        return ApiCommonResponse.builder()
                .status("조회 성공")
                .message("조회 성공")
                .data(productService.getDynamicProductInfo(searchText))
                .build();
    }

}

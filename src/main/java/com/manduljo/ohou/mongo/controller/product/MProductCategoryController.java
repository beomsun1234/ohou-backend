package com.manduljo.ohou.mongo.controller.product;

import com.manduljo.ohou.ApiCommonResponse;
import com.manduljo.ohou.mongo.constant.AcceptType;
import com.manduljo.ohou.mongo.service.product.MProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/product-categories", produces = AcceptType.API_V1)
public class MProductCategoryController {

  private final MProductCategoryService categoryService;

  @GetMapping("/tree")
  public ApiCommonResponse<MProductCategoryDto.FindTreeResponse> findTree() {
    MProductCategoryDto.FindTreeResponse response = MProductCategoryDto.FindTreeResponse.builder()
        .productCategoryTreeNodeList(categoryService.findTree())
        .build();

    return new ApiCommonResponse<>(String.valueOf(HttpStatus.OK.value()), "카테고리 트리 조회 성공", response);
  }

}

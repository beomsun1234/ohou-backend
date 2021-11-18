package com.manduljo.ohou.mongo.controller.category;

import com.manduljo.ohou.ApiCommonResponse;
import com.manduljo.ohou.mongo.constant.AcceptType;
import com.manduljo.ohou.mongo.service.category.ZCategoryCriteria;
import com.manduljo.ohou.mongo.service.category.ZCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
// @RequestMapping(value = "/mongo-api/categories", produces = AcceptType.API_V1)
@RequestMapping(value = "/mongo-api/category", produces = AcceptType.API_V1)
public class ZCategoryController {

  private final ZCategoryService categoryService;

  @GetMapping
  public ApiCommonResponse<ZCategoryDto.FindCategoryResponse> findCategory() {
    List<ZCategoryCriteria.FindCategoryInfo> categoryInfoList = categoryService.findCategoryInfoList();
    ZCategoryDto.FindCategoryResponse response = categoryInfoListToFindCategoryResponse(categoryInfoList);
    return new ApiCommonResponse<>(String.valueOf(HttpStatus.OK.value()), "카테고리 조회 성공", response);
  }

  private ZCategoryDto.FindCategoryResponse categoryInfoListToFindCategoryResponse(List<ZCategoryCriteria.FindCategoryInfo> categoryInfoList) {
    return ZCategoryDto.FindCategoryResponse.builder()
        .categoryList(categoryInfoListToCategoryItemList(categoryInfoList))
        .build();
  }

  private List<ZCategoryDto.FindCategoryResponse.CategoryItem> categoryInfoListToCategoryItemList(List<ZCategoryCriteria.FindCategoryInfo> categoryInfoList) {
    return categoryInfoList.stream()
        .map(this::categoryInfoToCategoryItem)
        .collect(Collectors.toUnmodifiableList());
  }

  private ZCategoryDto.FindCategoryResponse.CategoryItem categoryInfoToCategoryItem(ZCategoryCriteria.FindCategoryInfo categoryInfo) {
    return ZCategoryDto.FindCategoryResponse.CategoryItem.builder()
        .id(categoryInfo.getId())
        .categoryName(categoryInfo.getCategoryName())
        .parentCategoryId(categoryInfo.getParentCategoryId())
        .categoryList(categoryInfoListToCategoryItemList(categoryInfo.getCategoryInfoList()))
        .build();
  }

}

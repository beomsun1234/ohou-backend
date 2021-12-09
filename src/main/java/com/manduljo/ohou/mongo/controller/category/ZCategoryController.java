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
    ZCategoryCriteria.FindCategoryInfo info = categoryService.findCategoryInfo();
    ZCategoryDto.FindCategoryResponse response = toResponse(info);
    return new ApiCommonResponse<>(String.valueOf(HttpStatus.OK.value()), "카테고리 조회 성공", response);
  }

  private ZCategoryDto.FindCategoryResponse toResponse(ZCategoryCriteria.FindCategoryInfo info) {
    return ZCategoryDto.FindCategoryResponse.builder()
        .categoryList(toResponseItemList(info.getCategoryList()))
        .build();
  }

  private List<ZCategoryDto.FindCategoryResponse.Item> toResponseItemList(List<ZCategoryCriteria.FindCategoryInfo.Item> infoItemList) {
    return infoItemList.stream()
        .map(this::toResponseItem)
        .collect(Collectors.toUnmodifiableList());
  }

  private ZCategoryDto.FindCategoryResponse.Item toResponseItem(ZCategoryCriteria.FindCategoryInfo.Item infoItem) {
    return ZCategoryDto.FindCategoryResponse.Item.builder()
        .id(infoItem.getId())
        .categoryName(infoItem.getCategoryName())
        .parentCategoryId(infoItem.getParentCategoryId())
        .categoryList(toResponseItemList(infoItem.getCategoryList()))
        .build();
  }

}

package com.manduljo.ohou.mongo.controller.product;

import com.manduljo.ohou.ApiCommonResponse;
import com.manduljo.ohou.mongo.constant.AcceptType;
import com.manduljo.ohou.mongo.service.category.ZCategoryCriteria;
import com.manduljo.ohou.mongo.service.category.ZCategoryService;
import com.manduljo.ohou.mongo.service.product.ZProductCriteria;
import com.manduljo.ohou.mongo.service.product.ZProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mongo-api/store", produces = AcceptType.API_V1)
public class ZStoreController {

  private final ZCategoryService categoryService;

  private final ZProductService productService;

  @GetMapping
  public ApiCommonResponse<ZProductDto.FindProductByCategoryResponse> findProductByCategory(
      @RequestParam(name = "category", required = false) String categoryId,
      @RequestParam int page
  ) {
    ZProductCriteria.FindProductByCategoryCriteria criteria = toFindProductByCategoryCriteria(categoryId, page);
    ZProductCriteria.FindProductByCategoryResult result = productService.findProductByCategoryPageInfo(criteria);
    ZProductDto.FindProductByCategoryResponse response = toResponse(result);
    return new ApiCommonResponse<>(String.valueOf(HttpStatus.OK.value()), "상품 검색 성공", response);
  }

  private ZProductCriteria.FindProductByCategoryCriteria toFindProductByCategoryCriteria(String categoryId, int page) {
    return ZProductCriteria.FindProductByCategoryCriteria.builder()
        .categoryId(categoryId)
        .pageable(PageRequest.of(page, 15))
        .build();
  }

  private ZProductDto.FindProductByCategoryResponse toResponse(
      ZProductCriteria.FindProductByCategoryResult result
  ) {
    return ZProductDto.FindProductByCategoryResponse.builder()
        .categoryList(toResponseCategoryItemList(result.getCategoryInfo().getCategoryList()))
        .productPageInfo(toResponseProductPageInfo(result.getProductPageInfo()))
        .build();
  }

  private List<ZProductDto.FindProductByCategoryResponse.CategoryItem> toResponseCategoryItemList(
      List<ZCategoryCriteria.FindCategoryInfo.Item> categoryInfoItemList
  ) {
    return categoryInfoItemList.stream()
        .map(this::toResponseCategoryItem)
        .collect(Collectors.toUnmodifiableList());
  }

  private ZProductDto.FindProductByCategoryResponse.CategoryItem toResponseCategoryItem(
      ZCategoryCriteria.FindCategoryInfo.Item categoryInfoItem
  ) {
    return ZProductDto.FindProductByCategoryResponse.CategoryItem.builder()
        .id(categoryInfoItem.getId())
        .categoryName(categoryInfoItem.getCategoryName())
        .parentCategoryId(categoryInfoItem.getParentCategoryId())
        .categoryList(toResponseCategoryItemList(categoryInfoItem.getCategoryList()))
        .build();
  }

  private ZProductDto.FindProductByCategoryResponse.ProductPageInfo toResponseProductPageInfo(
      ZProductCriteria.FindProductByCategoryPageInfo productPageInfo
  ) {
    return ZProductDto.FindProductByCategoryResponse.ProductPageInfo.builder()
        .totalPage(productPageInfo.getTotalPage())
        .totalCount(productPageInfo.getTotalCount())
        .productList(toResponseProductPageInfoItemList(productPageInfo.getProductList()))
        .build();
  }

  private List<ZProductDto.FindProductByCategoryResponse.ProductPageInfo.Item> toResponseProductPageInfoItemList(
      List<ZProductCriteria.FindProductByCategoryPageInfo.Item> productPageInfoItemList
  ) {
    return productPageInfoItemList.stream()
        .map(this::toResponseProductPageInfoItem)
        .collect(Collectors.toUnmodifiableList());
  }

  private ZProductDto.FindProductByCategoryResponse.ProductPageInfo.Item toResponseProductPageInfoItem(
      ZProductCriteria.FindProductByCategoryPageInfo.Item productPageInfoItem
  ) {
    return ZProductDto.FindProductByCategoryResponse.ProductPageInfo.Item.builder()
        .id(productPageInfoItem.getId())
        .productName(productPageInfoItem.getProductName())
        .price(productPageInfoItem.getPrice())
        .thumbnailImage(productPageInfoItem.getThumbnailImage())
        .build();
  }

}

package com.manduljo.ohou.mongo.controller.product;

import com.manduljo.ohou.ApiCommonResponse;
import com.manduljo.ohou.mongo.constant.AcceptType;
import com.manduljo.ohou.mongo.service.product.ZProductCriteria;
import com.manduljo.ohou.mongo.service.product.ZProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mongo-api/products", produces = AcceptType.API_V1)
public class ZProductController {

  private final ZProductService productService;

  @GetMapping("/{id}")
  public ApiCommonResponse<ZProductDto.GetProductDetailResponse> getProductDetail(@PathVariable String id) {
    ZProductCriteria.GetProductDetailInfo info = productService.getProductDetailById(id);
    ZProductDto.GetProductDetailResponse response = productDetailInfoToGetProductDetailResponse(info);
    return new ApiCommonResponse<>(String.valueOf(HttpStatus.OK.value()), "상품 상세 조회 성공", response);
  }

  private ZProductDto.GetProductDetailResponse productDetailInfoToGetProductDetailResponse(ZProductCriteria.GetProductDetailInfo productDetailInfo) {
    return ZProductDto.GetProductDetailResponse.builder()
        .id(productDetailInfo.getId())
        .productName(productDetailInfo.getProductName())
        .price(productDetailInfo.getPrice())
        .thumbnailImage(productDetailInfo.getThumbnailImage())
        .productImageList(productDetailInfo.getProductImageList())
        .build();
  }

  @GetMapping("/search")
  public ApiCommonResponse<ZProductDto.FindProductResponse> findProductBySearchText(
      @RequestParam String searchText,
      @RequestParam(defaultValue = "0") int page
  ) {
    ZProductCriteria.FindProductBySearchTextCriteria criteria = makeFindProductBySearchTextCriteria(searchText, page);
    ZProductCriteria.FindProductBySearchTextPageInfo info = productService.findProductBySearchTextPageInfo(criteria);
    ZProductDto.FindProductResponse response = findProductBySearchTextPageInfoToFindProductResponse(info);
    return new ApiCommonResponse<>(String.valueOf(HttpStatus.OK.value()), "상품 검색 성공", response);
  }

  private ZProductCriteria.FindProductBySearchTextCriteria makeFindProductBySearchTextCriteria(String searchText, int page) {
    return ZProductCriteria.FindProductBySearchTextCriteria.builder()
        .searchText(searchText)
        .page(page)
        .build();
  }

  private ZProductDto.FindProductResponse findProductBySearchTextPageInfoToFindProductResponse(ZProductCriteria.FindProductBySearchTextPageInfo info) {
    return ZProductDto.FindProductResponse.builder()
        .totalPage(info.getTotalPage())
        .totalCount(info.getTotalCount())
        .productList(infoItemListToResponseItemList(info.getProductList()))
        .build();
  }

  private List<ZProductDto.FindProductResponse.Item> infoItemListToResponseItemList(List<ZProductCriteria.FindProductBySearchTextPageInfo.Item> infoItemList) {
    return infoItemList.stream()
        .map(this::infoItemToResponseItem)
        .collect(Collectors.toUnmodifiableList());
  }

  private ZProductDto.FindProductResponse.Item infoItemToResponseItem(ZProductCriteria.FindProductBySearchTextPageInfo.Item item) {
    return ZProductDto.FindProductResponse.Item.builder()
        .id(item.getId())
        .productName(item.getProductName())
        .price(item.getPrice())
        .thumbnailImage(item.getThumbnailImage())
        .build();
  }

}

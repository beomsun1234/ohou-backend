package com.manduljo.ohou.mongo.controller.product;

import com.manduljo.ohou.ApiCommonResponse;
import com.manduljo.ohou.mongo.constant.AcceptType;
import com.manduljo.ohou.mongo.service.product.ZProductCriteria;
import com.manduljo.ohou.mongo.service.product.ZProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mongo-api/product", produces = AcceptType.API_V1)
public class ZProductController {

  private final ZProductService productService;

  @GetMapping
  public ApiCommonResponse<ZProductDto.FindProductResponse> findCategory() {
    List<ZProductCriteria.FindProductInfo> productInfoList = productService.findProductInfoList();
    ZProductDto.FindProductResponse response = productInfoListToFindProductResponse(productInfoList);
    return new ApiCommonResponse<>(String.valueOf(HttpStatus.OK.value()), "상품 조회 성공", response);
  }

  private ZProductDto.FindProductResponse productInfoListToFindProductResponse(List<ZProductCriteria.FindProductInfo> productInfoList) {
    return ZProductDto.FindProductResponse.builder()
        .productList(productInfoListToProductItemList(productInfoList))
        .build();
  }

  private List<ZProductDto.FindProductResponse.ProductItem> productInfoListToProductItemList(List<ZProductCriteria.FindProductInfo> productInfoList) {
    return productInfoList.stream()
        .map(productInfo -> ZProductDto.FindProductResponse.ProductItem.builder()
            .id(productInfo.getId())
            .productName(productInfo.getProductName())
            .categoryId(productInfo.getCategoryId())
            .build())
        .collect(Collectors.toUnmodifiableList());
  }

}

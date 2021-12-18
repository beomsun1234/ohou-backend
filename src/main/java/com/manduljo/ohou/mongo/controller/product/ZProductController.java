package com.manduljo.ohou.mongo.controller.product;

import com.manduljo.ohou.ApiCommonResponse;
import com.manduljo.ohou.mongo.constant.AcceptType;
import com.manduljo.ohou.mongo.service.product.ZProductCriteria;
import com.manduljo.ohou.mongo.service.product.ZProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
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
    ZProductDto.GetProductDetailResponse response = toResponse(info);
    return new ApiCommonResponse<>(String.valueOf(HttpStatus.OK.value()), "상품 상세 조회 성공", response);
  }

  private ZProductDto.GetProductDetailResponse toResponse(ZProductCriteria.GetProductDetailInfo productDetailInfo) {
    return ZProductDto.GetProductDetailResponse.builder()
        .id(productDetailInfo.getId())
        .productName(productDetailInfo.getProductName())
        .price(productDetailInfo.getPrice())
        .thumbnailImage(productDetailInfo.getThumbnailImage())
        .mainImage(toMainImage(productDetailInfo.getCoverImageList()))
        .leftCoverImageList(toLeftCoverImageList(productDetailInfo.getCoverImageList()))
        .detailImage(productDetailInfo.getDetailImage())
        .build();
  }

  private String toMainImage(List<ZProductCriteria.GetProductDetailInfo.CoverImageItem> coverImageItemList) {
    return CollectionUtils.isEmpty(coverImageItemList) ? null : coverImageItemList.get(0).getMain();
  }

  private List<String> toLeftCoverImageList(List<ZProductCriteria.GetProductDetailInfo.CoverImageItem> coverImageItemList) {
    return CollectionUtils.isEmpty(coverImageItemList) ? null :
        coverImageItemList.stream()
            .map(ZProductCriteria.GetProductDetailInfo.CoverImageItem::getLeft)
            .collect(Collectors.toUnmodifiableList());
  }

  @GetMapping("/search")
  public ApiCommonResponse<ZProductDto.FindProductResponse> findProductBySearchText(
      @RequestParam(name = "search", required = false) String searchText,
      @RequestParam int page
  ) {
    ZProductCriteria.FindProductBySearchTextCriteria criteria = toFindProductBySearchTextCriteria(searchText, page);
    ZProductCriteria.FindProductBySearchTextPageInfo info = productService.findProductBySearchTextPageInfo(criteria);
    ZProductDto.FindProductResponse response = toResponse(info);
    return new ApiCommonResponse<>(String.valueOf(HttpStatus.OK.value()), "상품 검색 성공", response);
  }

  private ZProductCriteria.FindProductBySearchTextCriteria toFindProductBySearchTextCriteria(String searchText, int page) {
    return ZProductCriteria.FindProductBySearchTextCriteria.builder()
        .searchText(searchText)
        .pageable(PageRequest.of(page, 15, Sort.by(Sort.Order.desc("_id"))))
        .build();
  }

  private ZProductDto.FindProductResponse toResponse(ZProductCriteria.FindProductBySearchTextPageInfo info) {
    return ZProductDto.FindProductResponse.builder()
        .totalPage(info.getTotalPage())
        .totalCount(info.getTotalCount())
        .productList(toResponseItemList(info.getProductList()))
        .build();
  }

  private List<ZProductDto.FindProductResponse.Item> toResponseItemList(List<ZProductCriteria.FindProductBySearchTextPageInfo.Item> infoItemList) {
    return infoItemList.stream()
        .map(this::toResponseItem)
        .collect(Collectors.toUnmodifiableList());
  }

  private ZProductDto.FindProductResponse.Item toResponseItem(ZProductCriteria.FindProductBySearchTextPageInfo.Item infoItem) {
    return ZProductDto.FindProductResponse.Item.builder()
        .id(infoItem.getId())
        .productName(infoItem.getProductName())
        .price(infoItem.getPrice())
        .thumbnailImage(infoItem.getThumbnailImage())
        .build();
  }

}

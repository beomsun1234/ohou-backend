package com.manduljo.ohou.mongo.service.product;

import com.manduljo.ohou.mongo.domain.category.ZCategory;
import com.manduljo.ohou.mongo.domain.product.ZProduct;
import com.manduljo.ohou.mongo.repository.category.ZCategoryRepository;
import com.manduljo.ohou.mongo.repository.product.ZProductRepository;
import com.manduljo.ohou.mongo.repository.product.ZProductTemplateRepository;
import com.manduljo.ohou.mongo.service.category.ZCategoryCriteria;
import com.manduljo.ohou.mongo.service.category.ZCategoryService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ZProductService {

  private final ZProductRepository productRepository;

  private final ZProductTemplateRepository productTemplateRepository;

  private final ZCategoryService categoryService;

  private final ZCategoryRepository categoryRepository;

  public ZProductCriteria.GetProductDetailInfo getProductDetailById(String id) {
    ZProduct product = productRepository.findById(id).orElseThrow();
    return ZProductCriteria.GetProductDetailInfo.builder()
        .id(product.getId())
        .productName(product.getProductName())
        .price(product.getPrice())
        .thumbnailImage(product.getThumbnailImage())
        .productImageList(product.getProductImageList())
        .build();
  }

  public ZProductCriteria.FindProductBySearchTextPageInfo findProductBySearchTextPageInfo(ZProductCriteria.FindProductBySearchTextCriteria criteria) {
    String searchText = criteria.getSearchText();
    Pageable pageable = criteria.getPageable();
    Page<ZProduct> productPage = StringUtils.hasText(searchText) ?
        productRepository.findByProductNameContains(searchText, pageable) :
        productRepository.findAll(pageable);
    return ZProductCriteria.FindProductBySearchTextPageInfo.builder()
        .totalPage(productPage.getTotalPages())
        .totalCount((int) productPage.getTotalElements())
        .productList(productPage.stream()
            .map(product -> ZProductCriteria.FindProductBySearchTextPageInfo.Item.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .thumbnailImage(product.getThumbnailImage())
                .build()
            )
            .collect(Collectors.toUnmodifiableList())
        )
        .build();
  }

  public ZProductCriteria.FindProductByCategoryResult findProductByCategoryPageInfo(
      ZProductCriteria.FindProductByCategoryCriteria criteria
  ) {
    ZCategoryCriteria.FindCategoryInfo categoryInfo = categoryService.findCategoryInfo();

    List<ZCategory> categoryList = categoryRepository.findByAncestorIdList(new ObjectId(criteria.getCategoryId()));
    List<String> categoryChildrenIdList = categoryList.stream().map(ZCategory::getId).collect(Collectors.toUnmodifiableList());

    Page<ZProduct> productPage = productRepository.findByCategoryIdIn(categoryChildrenIdList, criteria.getPageable());

    ZProductCriteria.FindProductByCategoryPageInfo productPageInfo = ZProductCriteria.FindProductByCategoryPageInfo.builder()
        .totalPage(productPage.getTotalPages())
        .totalCount((int) productPage.getTotalElements())
        .productList(productPage.stream()
            .map(product -> ZProductCriteria.FindProductByCategoryPageInfo.Item.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .thumbnailImage(product.getThumbnailImage())
                .build()
            )
            .collect(Collectors.toUnmodifiableList())
        )
        .build();

    return ZProductCriteria.FindProductByCategoryResult.builder()
        .categoryInfo(categoryInfo)
        .productPageInfo(productPageInfo)
        .build();
  }

}

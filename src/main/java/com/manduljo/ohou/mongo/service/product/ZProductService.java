package com.manduljo.ohou.mongo.service.product;

import com.manduljo.ohou.mongo.domain.product.ZProduct;
import com.manduljo.ohou.mongo.repository.product.ZProductRepository;
import com.manduljo.ohou.mongo.repository.product.ZProductTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ZProductService {

  private final ZProductRepository productRepository;

  private final ZProductTemplateRepository productTemplateRepository;

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
    Pageable pageable = PageRequest.of(criteria.getPage(), 15, Sort.by(Sort.Order.desc("_id")));
    Page<ZProduct> productPage = productRepository.findByProductNameContains(criteria.getSearchText(), pageable);
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
}

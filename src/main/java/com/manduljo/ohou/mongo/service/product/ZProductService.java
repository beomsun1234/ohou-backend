package com.manduljo.ohou.mongo.service.product;

import com.manduljo.ohou.mongo.domain.product.ZProduct;
import com.manduljo.ohou.mongo.repository.product.ZProductRepository;
import com.manduljo.ohou.mongo.repository.product.ZProductTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ZProductService {

  private final ZProductRepository productRepository;

  private final ZProductTemplateRepository productTemplateRepository;

  public List<ZProductCriteria.FindProductInfo> findProductInfoList() {
    List<ZProduct> productList = productRepository.findAll();
    List<ZProductCriteria.FindProductInfo> productInfoList = productList.stream()
        .map(product -> ZProductCriteria.FindProductInfo.builder()
            .id(product.getId())
            .productName(product.getProductName())
            .categoryId(product.getCategoryId())
            .build())
        .collect(Collectors.toUnmodifiableList());
    return productInfoList;
  }

}

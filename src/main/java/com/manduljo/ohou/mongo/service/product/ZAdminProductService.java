package com.manduljo.ohou.mongo.service.product;

import com.manduljo.ohou.mongo.repository.category.ZCategoryRepository;
import com.manduljo.ohou.mongo.repository.product.ZProductRepository;
import com.manduljo.ohou.mongo.repository.product.ZProductTemplateRepository;
import com.manduljo.ohou.mongo.service.category.ZCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ZAdminProductService {

  private final ZProductRepository productRepository;

  private final ZProductTemplateRepository productTemplateRepository;

  private final ZCategoryService categoryService;

  private final ZCategoryRepository categoryRepository;

}

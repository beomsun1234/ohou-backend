package com.manduljo.ohou.mongo.service.category;

import com.manduljo.ohou.mongo.repository.category.ZCategoryRepository;
import com.manduljo.ohou.mongo.repository.category.ZCategoryTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ZAdminCategoryService {

  private final ZCategoryRepository categoryRepository;

  private final ZCategoryTemplateRepository categoryTemplateRepository;

}

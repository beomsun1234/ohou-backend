package com.manduljo.ohou.mongo.service.category;

import com.manduljo.ohou.mongo.domain.category.ZCategory;
import com.manduljo.ohou.mongo.repository.category.ZCategoryRepository;
import com.manduljo.ohou.mongo.repository.category.ZCategoryTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ZCategoryService {

  private final ZCategoryRepository categoryRepository;

  private final ZCategoryTemplateRepository categoryTemplateRepository;

  public List<ZCategoryCriteria.FindCategoryInfo> findCategoryInfoList() {
    Map<String, ZCategoryCriteria.FindCategoryInfo> categoryInfoMap = new HashMap<>();
    List<ZCategoryCriteria.FindCategoryInfo> rootCategoryInfoList = new ArrayList<>();

    List<ZCategory> categoryList = categoryRepository.findAll();
    categoryList.sort(Comparator.comparingInt(o -> o.getAncestorIdList().size()));

    for (ZCategory category : categoryList) {
      ZCategoryCriteria.FindCategoryInfo categoryInfo = ZCategoryCriteria.FindCategoryInfo.builder()
          .id(category.getId())
          .categoryName((category.getCategoryName()))
          .parentCategoryId(category.getParentCategoryId() == null ? "root" : category.getParentCategoryId()) // mariadb api 와 동일한 형태로 맞추기 위해 root 삽입
          .categoryInfoList(new ArrayList<>())
          .build();

      categoryInfoMap.put(categoryInfo.getId(), categoryInfo);

      if (category.getParentCategoryId() == null) {
        rootCategoryInfoList.add(categoryInfo);
      } else {
        categoryInfoMap.get(category.getParentCategoryId()).getCategoryInfoList().add(categoryInfo);
      }
    }

    return rootCategoryInfoList;
  }

}

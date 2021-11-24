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

  public ZCategoryCriteria.FindCategoryInfo findCategoryInfo() {
    Map<String, ZCategoryCriteria.FindCategoryInfo.Item> categoryInfoMap = new HashMap<>();
    List<ZCategoryCriteria.FindCategoryInfo.Item> rootCategoryInfoItemList = new ArrayList<>();

    List<ZCategory> categoryList = categoryRepository.findAll();
    categoryList.sort(Comparator.comparingInt(o -> o.getAncestorIdList().size()));

    for (ZCategory category : categoryList) {
      ZCategoryCriteria.FindCategoryInfo.Item item = ZCategoryCriteria.FindCategoryInfo.Item.builder()
          .id(category.getId())
          .categoryName((category.getCategoryName()))
          .parentCategoryId(category.getParentCategoryId() == null ? "root" : category.getParentCategoryId()) // mariadb api 와 동일한 형태로 맞추기 위해 root 삽입
          .categoryList(new ArrayList<>())
          .build();

      categoryInfoMap.put(item.getId(), item);

      if (category.getParentCategoryId() == null) {
        rootCategoryInfoItemList.add(item);
      } else {
        categoryInfoMap.get(category.getParentCategoryId()).getCategoryList().add(item);
      }
    }

    return ZCategoryCriteria.FindCategoryInfo.builder()
        .categoryList(rootCategoryInfoItemList)
        .build();
  }

}

package com.manduljo.ohou.mongo.service.product;

import com.manduljo.ohou.mongo.controller.product.MProductCategoryDto;
import com.manduljo.ohou.mongo.domain.product.MProductCategory;
import com.manduljo.ohou.mongo.repository.product.MProductCategoryRepository;
import com.manduljo.ohou.mongo.repository.product.MProductCategoryTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MProductCategoryService {

  private final MProductCategoryRepository productCategoryRepository;

  private final MProductCategoryTemplateRepository productCategoryTemplateRepository;

  public List<MProductCategoryDto.FindTreeResponse.ProductCategoryTreeNode> findTree() {
    Map<String, MProductCategoryDto.FindTreeResponse.ProductCategoryTreeNode> NodeMap = new HashMap<>();
    List<MProductCategoryDto.FindTreeResponse.ProductCategoryTreeNode> rootNodeList = new ArrayList<>();

    List<MProductCategory> productCategoryList = productCategoryRepository.findAll();
    productCategoryList.sort(Comparator.comparingInt(o -> o.getAncestorIdList().size()));

    for (MProductCategory productCategory : productCategoryList) {
      MProductCategoryDto.FindTreeResponse.ProductCategoryTreeNode treeNode =
          MProductCategoryDto.FindTreeResponse.ProductCategoryTreeNode.builder()
              .id(productCategory.getId())
              .productCategoryName((productCategory.getProductCategoryName()))
              .productCategoryTreeNodeList(new ArrayList<>())
              .build();

      NodeMap.put(treeNode.getId(), treeNode);

      if (productCategory.getParentProductCategoryId() == null) {
        rootNodeList.add(treeNode);
      } else {
        NodeMap.get(productCategory.getParentProductCategoryId()).getProductCategoryTreeNodeList().add(treeNode);
      }
    }

    return rootNodeList;
  }

}

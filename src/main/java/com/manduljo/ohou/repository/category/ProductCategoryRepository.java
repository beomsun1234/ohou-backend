package com.manduljo.ohou.repository.category;

import com.manduljo.ohou.domain.category.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, String> {

}

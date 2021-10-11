package com.manduljo.ohou.repository.product;

import com.manduljo.ohou.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdcutRepository extends JpaRepository<Product,Long> {
}

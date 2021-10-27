package com.manduljo.ohou.repository.product;

import com.manduljo.ohou.domain.category.ProductCategory;
import com.manduljo.ohou.domain.product.Product;
import com.manduljo.ohou.repository.category.ProductCategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@DataJpaTest
class ProdcutRepositoryTest {

    @Autowired
    private ProductRepository prodcutRepository;

    @DisplayName("crud test")
    @Test
    void save(){
        //given
        Product product = Product.builder().name("이단소파").price(1000).productCategory(ProductCategory.builder().build()).build();
        //when
        Product savedProduct = prodcutRepository.save(product);
        //then
        Assertions.assertThat(savedProduct.getName()).isEqualTo(product.getName());
    }

}
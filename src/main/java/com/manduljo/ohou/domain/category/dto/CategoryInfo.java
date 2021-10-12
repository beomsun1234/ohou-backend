package com.manduljo.ohou.domain.category.dto;

import com.manduljo.ohou.domain.category.ProductCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class CategoryInfo implements Serializable {
    private String id;
    private String name;

    @Builder
    public CategoryInfo(ProductCategory entity){
        this.id=entity.getId();
        this.name = entity.getName();
    }
}

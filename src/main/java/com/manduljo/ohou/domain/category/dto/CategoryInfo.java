package com.manduljo.ohou.domain.category.dto;

import com.manduljo.ohou.domain.category.ProductCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryInfo {
    private String id;
    private String name;

    @Builder
    public CategoryInfo(ProductCategory entity){
        this.id=entity.getId();
        this.name = entity.getName();
    }
}

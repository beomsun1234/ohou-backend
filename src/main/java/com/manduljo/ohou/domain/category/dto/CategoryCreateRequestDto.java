package com.manduljo.ohou.domain.category.dto;

import com.manduljo.ohou.domain.category.ProductCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryCreateRequestDto {
    private String id;
    private String name;
    private String parentId;

    @Builder
    public CategoryCreateRequestDto(String id, String name, String parentId){
        this.id=id;
        this.name = name;
        this.parentId = parentId;
    }

    public ProductCategory toEntity(ProductCategory parent){
        return ProductCategory.builder()
                .id(id)
                .name(name)
                .parentCategory(parent)
                .build();
    }
}

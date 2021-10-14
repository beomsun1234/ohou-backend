package com.manduljo.ohou.domain.category.dto;

import com.manduljo.ohou.domain.category.ProductCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
public class CategoryInfo implements Serializable {
    private String id;
    private String name;
    private String parentId;
    private List<CategoryInfo> child = new ArrayList<>();

    @Builder
    public CategoryInfo(ProductCategory entity){
        this.id=entity.getId();
        this.name = entity.getName();
        if(entity.getParentCategory() == null){
            this.parentId = "root";
        }
        else{
            this.parentId = entity.getParentCategory().getId();
        }
    }

}

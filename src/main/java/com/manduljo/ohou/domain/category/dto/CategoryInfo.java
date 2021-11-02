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
    private String title;
    private String key;
    private String parentId;
    private List<CategoryInfo> children = new ArrayList<>();

    @Builder
    public CategoryInfo(ProductCategory entity){
        this.title = entity.getName();
        this.key=entity.getId();
        if(entity.getParentCategory() == null){
            this.parentId = "root";
        }
        else{
            this.parentId = entity.getParentCategory().getId();
        }
    }

}

package com.manduljo.ohou.domain.product;

import com.manduljo.ohou.domain.BaseTimeEntity;
import com.manduljo.ohou.domain.category.ProductCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int price;

    @Transient
    //영속성 관리 x AND 테이블 매핑 x
    private String thumbnailImage;

    @Transient
    //영속성 관리 x AND 테이블 매핑 x
    private final List<String> productImage = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_category_id")
    private ProductCategory productCategory;

    @Builder
    public Product(Long id,String name, int price, ProductCategory productCategory){
        this.id = id;
        this.name = name;
        this.price = price;
        //this.thumbnailImage = thumbnailImage;
        this.productCategory = productCategory;
    }

    //데이터 조회시 이미지 경로 저장(테이블 만들지 않기 위해 사용) but 나중에 이미지 테이블 만들어야함
    @PostLoad
    public void setProductImages(){
        for (int i = 1; i<=3; i++){
            this.productImage.add("ProductImage/"+getId()+"/"+i+".png");
        }
        this.thumbnailImage = "ProductImage/"+getId()+"/"+"thumbnail.png";
    }

}

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
    //캐러셀 메인 이미지 556x556
    private String mainImage;

    @Transient
    //영속성 관리 x AND 테이블 매핑 x
    //좌측 커버이미지 58x58
    private final List<String> leftCoverImage = new ArrayList<>();

    //상세 이미지
    // 692x600
    @Transient
    private String productDetailImage;

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
        this.thumbnailImage = "via.placeholder.com/265x331?text="+getId(); //썸네일은 아직 못받음
        this.mainImage = "via.placeholder.com/556x556?text="+getId();
        for (int i = 1; i<=3; i++){
            this.leftCoverImage.add("via.placeholder.com/58x58?text="+getId());
        }
        this.productDetailImage = "via.placeholder.com/692x600?text="+getId();
    }

}

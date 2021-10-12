package com.manduljo.ohou.controller;

import com.manduljo.ohou.ApiComonResponse;
import com.manduljo.ohou.domain.category.dto.CategoryInfo;
import com.manduljo.ohou.domain.product.Product;
import com.manduljo.ohou.domain.product.dto.ProductInfo;
import com.manduljo.ohou.repository.category.ProductCategoryQueryRepository;
import com.manduljo.ohou.repository.product.ProductQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("api")
@RequiredArgsConstructor
public class StoreController {
    private final ProductQueryRepository productQueryRepository;
    private final ProductCategoryQueryRepository productCategoryQueryRepository;


    /**
     * 메인페이지를 위해서
     * 부모카테고리 선택(디폴트는 가구) -> 하위 카테고리 및 가구에 대한 상품 조회
     * @param id
     * @return
     */
    @GetMapping("store")
    public ApiComonResponse getProductInfoAndCategoryInfo(@RequestParam(name = "category",defaultValue = "0")String id){
        //0-> 가구 ->  0_
        //1-> 패브릭   1_
        //2-> 조명     2_
        //3-> 가전     3_
        Map<String, Object> data = new HashMap<>();
        if(!id.contains("_")){
            //부모일경우 자시 카테고리 가져오기
            List<CategoryInfo> categoryInfos = productCategoryQueryRepository.findCategoryByParentId(id)
                    .stream()
                    .map(productCategory -> CategoryInfo.builder().entity(productCategory).build())
                    .collect(Collectors.toList());
            data.put("category",categoryInfos);
        }
        List<ProductInfo> productInfos = productQueryRepository.findByCategoryId(id)
                .stream()
                .map(product -> ProductInfo.builder().product(product).build())
                .collect(Collectors.toList());
        data.put("product",productInfos);
        return ApiComonResponse.builder()
                .status(String.valueOf(HttpStatus.OK.value()))
                .message("성공")
                .data(data)
                .build();
    }

    /**
     * 검색
     * @param searchText
     * @return
     */
    @GetMapping("store/search")
    public ApiComonResponse getDynamicProductInfo(@RequestParam(name = "search", required = false, defaultValue = "") String searchText){
        return ApiComonResponse.builder()
                .status(String.valueOf(HttpStatus.OK.value()))
                .message("성공")
                .data(productQueryRepository.findByProdocutNameOrCategoryNameOrParentCategoryNameContaining(searchText)
                        .stream()
                        .map(product -> ProductInfo.builder().product(product).build())
                        .collect(Collectors.toList()))
                .build();
    }
}

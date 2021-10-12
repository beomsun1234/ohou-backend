package com.manduljo.ohou.controller;

import com.manduljo.ohou.ApiCommonResponse;
import com.manduljo.ohou.service.ProductCategoryService;
import com.manduljo.ohou.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@Slf4j
@RequestMapping("api")
@RequiredArgsConstructor
public class StoreController {
    private final ProductCategoryService productCategoryService;
    private final ProductService productService;

    /**
     *  0-> 가구 ->  0_
     *  1-> 패브릭   1_
     *  2-> 조명     2_
     *  3-> 가전     3_
     * 메인페이지를 위해서
     * 부모카테고리 선택(디폴트는 가구) -> 하위 카테고리 및 가구에 대한 상품 조회
     * @param id
     * @return
     */
    @GetMapping("store")
    public ApiCommonResponse getProductInfoAndCategoryInfo(@RequestParam(name = "category",defaultValue = "0")String id){
        Map<String, Object> data = new HashMap<>();
        if(!id.contains("_")){
            //부모일경우 자식 카테고리 가져오기
            data.put("category",productCategoryService.findCategoryById(id));
        }
        data.put("product",productService.findProductByCategoryId(id));
        return ApiCommonResponse.builder()
                .status(String.valueOf(HttpStatus.OK.value()))
                .message("성공")
                .data(data)
                .build();
    }


}

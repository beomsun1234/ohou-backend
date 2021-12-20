package com.manduljo.ohou.controller;


import com.manduljo.ohou.ApiCommonResponse;
import com.manduljo.ohou.domain.product.dto.ProductCreateDto;
import com.manduljo.ohou.service.AdminService;
import com.manduljo.ohou.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final ProductService productService;

    /**
     * 상품생성
     * @param productCreateDto
     * @return
     */
    @PostMapping("products")
    public ResponseEntity<ApiCommonResponse> createProduct(@RequestBody ProductCreateDto productCreateDto){
        productService.createProduct(productCreateDto);
        return new ResponseEntity<>(ApiCommonResponse
                .builder()
                .data("상품이 등록되었습니다")
                .message("상품이 등록되었습니다")
                .status(String.valueOf(HttpStatus.OK.value()))
                .build(),HttpStatus.OK);
    }

    /**
     * 유저 전체 조회
     * @return
     */
    @GetMapping("members")
    public ResponseEntity<ApiCommonResponse> findAll(){
        return new ResponseEntity<>(ApiCommonResponse
                .builder()
                .data(adminService.findAll())
                .message("조회 성공")
                .status(String.valueOf(HttpStatus.OK.value()))
                .build(),HttpStatus.OK);
    }
    /**
     * 유저 이메일 조회
     */
    @GetMapping("members/search")
    public ResponseEntity<ApiCommonResponse> findByEmail(@RequestParam String search){
        return new ResponseEntity<>(ApiCommonResponse
                .builder()
                .data(adminService.findEmail(search))
                .message("조회 성공")
                .status(String.valueOf(HttpStatus.OK.value()))
                .build(),HttpStatus.OK);
    }
    /**
     * 상세보기
     */
    @GetMapping("members/{id}")
    public ResponseEntity<ApiCommonResponse> findById(@PathVariable Long id) {
        return new ResponseEntity<>(ApiCommonResponse
                .builder()
                .data(adminService.findById(id))
                .message("조회 성공")
                .status(String.valueOf(HttpStatus.OK.value()))
                .build(), HttpStatus.OK);
    }

    /**
     * 유저 벤
     */
    @CacheEvict(value="test", allEntries=true)
    @PutMapping("members/{id}")
    public ResponseEntity<ApiCommonResponse> benUser(@PathVariable Long id) {
        adminService.benUser(id);
        return new ResponseEntity<>(ApiCommonResponse
                .builder()
                .data("유저 탈퇴 성공")
                .message("유저 탈퇴 성공")
                .status(String.valueOf(HttpStatus.OK.value()))
                .build(), HttpStatus.OK);
    }

}

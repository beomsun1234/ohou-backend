package com.manduljo.ohou.mongo.controller.member;

import com.manduljo.ohou.ApiCommonResponse;
import com.manduljo.ohou.mongo.constant.AcceptType;
import com.manduljo.ohou.mongo.service.member.ZCartCriteria;
import com.manduljo.ohou.mongo.service.member.ZCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mongo-api/members", produces = AcceptType.API_V1)
public class ZCartController {

  private final ZCartService cartService;

  @GetMapping("/{id}/cart")
  public ApiCommonResponse<ZCartDto.FindCartByMemberIdResponse> findCartByMemberId(@PathVariable(name = "id") String memberId) {
    ZCartCriteria.FindCartByMemberIdInfo info = cartService.findCartByMemberId(memberId);
    ZCartDto.FindCartByMemberIdResponse response = toResponse(info);
    return new ApiCommonResponse<>(String.valueOf(HttpStatus.OK.value()), "카트 조회 성공", response);
  }

  private ZCartDto.FindCartByMemberIdResponse toResponse(ZCartCriteria.FindCartByMemberIdInfo info) {
    return ZCartDto.FindCartByMemberIdResponse.builder()
        .totalPrice(info.getTotalPrice())
        .cartItemList(toResponseItemList(info.getCartItemList()))
        .build();
  }

  private List<ZCartDto.FindCartByMemberIdResponse.Item> toResponseItemList(List<ZCartCriteria.FindCartByMemberIdInfo.Item> cartItemList) {
    return cartItemList.stream()
        .map(this::toResponseItem)
        .collect(Collectors.toUnmodifiableList());
  }

  private ZCartDto.FindCartByMemberIdResponse.Item toResponseItem(ZCartCriteria.FindCartByMemberIdInfo.Item cartItem) {
    return ZCartDto.FindCartByMemberIdResponse.Item.builder()
        .cartItemId(cartItem.getCartItemId())
        .productId(cartItem.getProductId())
        .productName(cartItem.getProductName())
        .thumbnailImage(cartItem.getThumbnailImage())
        .price(cartItem.getPrice())
        .quantity(cartItem.getQuantity())
        .totalPrice(cartItem.getTotalPrice())
        .build();
  }

}

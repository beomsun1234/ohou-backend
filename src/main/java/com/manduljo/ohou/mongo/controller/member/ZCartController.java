package com.manduljo.ohou.mongo.controller.member;

import com.manduljo.ohou.ApiCommonResponse;
import com.manduljo.ohou.mongo.constant.AcceptType;
import com.manduljo.ohou.mongo.service.member.ZCartCommand;
import com.manduljo.ohou.mongo.service.member.ZCartCriteria;
import com.manduljo.ohou.mongo.service.member.ZCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mongo-api/members", produces = AcceptType.API_V1)
public class ZCartController {

  private final ZCartService cartService;

  @GetMapping("/{id}/cart")
  public ApiCommonResponse<ZCartDto.FindCartItemByMemberIdResponse> findCartItemByMemberId(@PathVariable(name = "id") String memberId) {
    ZCartCriteria.FindCartItemByMemberIdInfo info = cartService.findCartItemByMemberId(memberId);
    ZCartDto.FindCartItemByMemberIdResponse response = toResponse(info);
    return new ApiCommonResponse<>(String.valueOf(HttpStatus.OK.value()), "카트 조회 성공", response);
  }

  private ZCartDto.FindCartItemByMemberIdResponse toResponse(ZCartCriteria.FindCartItemByMemberIdInfo info) {
    return ZCartDto.FindCartItemByMemberIdResponse.builder()
        .totalPrice(info.getTotalPrice())
        .cartItemList(toResponseItemList(info.getCartItemList()))
        .build();
  }

  private List<ZCartDto.FindCartItemByMemberIdResponse.Item> toResponseItemList(List<ZCartCriteria.FindCartItemByMemberIdInfo.Item> cartItemList) {
    return cartItemList.stream()
        .map(this::toResponseItem)
        .collect(Collectors.toUnmodifiableList());
  }

  private ZCartDto.FindCartItemByMemberIdResponse.Item toResponseItem(ZCartCriteria.FindCartItemByMemberIdInfo.Item cartItem) {
    return ZCartDto.FindCartItemByMemberIdResponse.Item.builder()
        .cartItemId(cartItem.getCartItemId())
        .productId(cartItem.getProductId())
        .productName(cartItem.getProductName())
        .thumbnailImage(cartItem.getThumbnailImage())
        .price(cartItem.getPrice())
        .quantity(cartItem.getQuantity())
        .totalPrice(cartItem.getTotalPrice())
        .build();
  }

  @PostMapping("/{id}/cart")
  public ApiCommonResponse<String> addCartItem(
      @PathVariable(name = "id") String memberId,
      @RequestBody ZCartDto.AddCartItemRequest addCartItemRequest
  ) {
    ZCartCommand.AddCartItemCommand command = toCommand(memberId, addCartItemRequest);
    String cartItemId = cartService.addCartItem(command);
    return new ApiCommonResponse<>(String.valueOf(HttpStatus.OK.value()), "카트 아이템 추가 성공", cartItemId);
  }

  private ZCartCommand.AddCartItemCommand toCommand(String memberId, ZCartDto.AddCartItemRequest addCartItemRequest) {
    return ZCartCommand.AddCartItemCommand.builder()
        .memberId(memberId)
        .productId(addCartItemRequest.getProductId())
        .productQuantity(addCartItemRequest.getProductQuantity())
        .build();
  }
}

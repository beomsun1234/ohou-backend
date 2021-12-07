package com.manduljo.ohou.mongo.controller.cart;

import com.manduljo.ohou.ApiCommonResponse;
import com.manduljo.ohou.mongo.constant.AcceptType;
import com.manduljo.ohou.mongo.service.cart.ZCartCommand;
import com.manduljo.ohou.mongo.service.cart.ZCartCriteria;
import com.manduljo.ohou.mongo.service.cart.ZCartService;
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
  public ApiCommonResponse<String> createCartItem(
      @PathVariable(name = "id") String memberId,
      @RequestBody ZCartDto.CreateCartItemRequest request
  ) {
    ZCartCommand.CreateCartItemCommand command = toCommand(memberId, request);
    String cartItemId = cartService.createCartItem(command);
    return new ApiCommonResponse<>(String.valueOf(HttpStatus.OK.value()), "카트 아이템 추가 성공", cartItemId);
  }

  private ZCartCommand.CreateCartItemCommand toCommand(String memberId, ZCartDto.CreateCartItemRequest request) {
    return ZCartCommand.CreateCartItemCommand.builder()
        .memberId(memberId)
        .productId(request.getProductId())
        .productQuantity(request.getProductQuantity())
        .build();
  }

  @PutMapping("/{id}/cart")
  public ApiCommonResponse<String> updateCartItemQuantity(
      @PathVariable(name = "id") String memberId,
      @RequestBody ZCartDto.UpdateCartItemProductQuantityRequest request
  ) {
    ZCartCommand.UpdateCartItemProductQuantityCommand command = toCommand(memberId, request);
    String cartItemId = cartService.updateCartItemProductQuantity(command);
    return new ApiCommonResponse<>(String.valueOf(HttpStatus.OK.value()), "카트 아이템 수량 변경 성공", cartItemId);
  }

  private ZCartCommand.UpdateCartItemProductQuantityCommand toCommand(String memberId, ZCartDto.UpdateCartItemProductQuantityRequest request) {
    return ZCartCommand.UpdateCartItemProductQuantityCommand.builder()
        .memberId(memberId)
        .cartItemId(request.getCartItemId())
        .productQuantity(request.getProductQuantity())
        .build();
  }
}

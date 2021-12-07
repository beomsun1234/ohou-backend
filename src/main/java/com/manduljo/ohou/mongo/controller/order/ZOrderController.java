package com.manduljo.ohou.mongo.controller.order;

import com.manduljo.ohou.ApiCommonResponse;
import com.manduljo.ohou.mongo.constant.AcceptType;
import com.manduljo.ohou.mongo.service.order.ZOrderCommand;
import com.manduljo.ohou.mongo.service.order.ZOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mongo-api/members", produces = AcceptType.API_V1)
public class ZOrderController {

  private final ZOrderService orderService;

  @PostMapping("/{id}/cart/orders")
  public ApiCommonResponse<ZOrderDto.CreateCartOrderResponse> createCartOrder(
      @PathVariable(name = "id") String memberId,
      @RequestBody ZOrderDto.CreateCartOrderRequest request
  ) {
    ZOrderCommand.CreateCartOrderCommand command = toCommand(memberId, request);
    ZOrderCommand.CreateCartOrderInfo info = orderService.createCartOrder(command);
    ZOrderDto.CreateCartOrderResponse response = toResponse(info);
    return new ApiCommonResponse<>(String.valueOf(HttpStatus.OK.value()), "카트 주문 성공", response);
    // return ApiCommonResponse.builder()
    //     .data("주문 성공")
    //     .message("장바구니 주문 성공")
    //     .status(String.valueOf(HttpStatus.OK))
    //     .build();
  }

  private ZOrderDto.CreateCartOrderResponse toResponse(ZOrderCommand.CreateCartOrderInfo info) {
    return ZOrderDto.CreateCartOrderResponse
        .builder().build();
  }

  private ZOrderCommand.CreateCartOrderCommand toCommand(String memberId, ZOrderDto.CreateCartOrderRequest request) {
    return ZOrderCommand.CreateCartOrderCommand.builder()
        .memberId(memberId)
        .cartItemIdList(request.getCartItemIdList())
        .email(request.getEmail())
        .name(request.getName())
        .phone(request.getPhone())
        .shippingAddress(toCommandShippingAddress(request.getShippingAddress()))
        .build();
  }

  private ZOrderCommand.ShippingAddress toCommandShippingAddress(ZOrderDto.ShippingAddress shippingAddress) {
    return ZOrderCommand.ShippingAddress.builder()
        .address(shippingAddress.getAddress())
        .addressDetail(shippingAddress.getAddressDetail())
        .zipCode(shippingAddress.getZipCode())
        .build();
  }

  @PostMapping("/{id}/orders")
  public ApiCommonResponse<ZOrderDto.CreateOrderResponse> createOrder(
      @PathVariable(name = "id") String memberId,
      @RequestBody ZOrderDto.CreateOrderRequest request
  ) {
    ZOrderCommand.CreateOrderCommand command = toCommand(memberId, request);
    ZOrderCommand.CreateOrderInfo info = orderService.createOrder(command);
    ZOrderDto.CreateOrderResponse response = toResponse(info);
    return new ApiCommonResponse<>(String.valueOf(HttpStatus.OK.value()), "주문 성공", response);
  }

  private ZOrderCommand.CreateOrderCommand toCommand(String memberId, ZOrderDto.CreateOrderRequest request) {
    return ZOrderCommand.CreateOrderCommand.builder()
        .memberId(memberId)
        .productId(CollectionUtils.isEmpty(request.getProductIdList()) ? null : request.getProductIdList().get(0))
        .email(request.getEmail())
        .name(request.getName())
        .phone(request.getPhone())
        .price(request.getPrice())
        .quantity(request.getQuantity())
        .shippingAddress(toCommandShippingAddress(request.getShippingAddress()))
        .build();
  }

  private ZOrderDto.CreateOrderResponse toResponse(ZOrderCommand.CreateOrderInfo info) {
    return ZOrderDto.CreateOrderResponse.builder()
        .orderId(info.getOrderId())
        .memberId(info.getMemberId())
        .shippingAddress(toShippingAddress(info))
        .totalPrice(info.getTotalPrice())
        .orderItemList(toInfoItemList(info.getOrderItemList()))
        .build();
  }

  private ZOrderDto.ShippingAddress toShippingAddress(ZOrderCommand.CreateOrderInfo info) {
    return ZOrderDto.ShippingAddress.builder()
        .zipCode(info.getShippingAddress().getZipCode())
        .address(info.getShippingAddress().getAddress())
        .addressDetail(info.getShippingAddress().getAddressDetail())
        .build();
  }

  private List<ZOrderDto.CreateOrderResponse.Item> toInfoItemList(List<ZOrderCommand.CreateOrderInfo.Item> orderItemList) {
    return orderItemList.stream()
        .map(this::toResponseItem)
        .collect(Collectors.toUnmodifiableList());
  }

  private ZOrderDto.CreateOrderResponse.Item toResponseItem(ZOrderCommand.CreateOrderInfo.Item item) {
    return ZOrderDto.CreateOrderResponse.Item.builder()
        .orderItemId(item.getOrderItemId())
        .productId(item.getProductId())
        .productName(item.getProductName())
        .productPrice(item.getProductPrice())
        .productQuantity(item.getProductQuantity())
        .build();
  }

}

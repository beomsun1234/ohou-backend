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

  private ZOrderDto.CreateCartOrderResponse toResponse(ZOrderCommand.CreateCartOrderInfo info) {
    return ZOrderDto.CreateCartOrderResponse.builder()
        .orderId(info.getOrderId())
        .memberId(info.getMemberId())
        .shippingAddress(toResponseShippingAddress(info))
        .totalPrice(info.getTotalPrice())
        .orderItemList(toCreateCartOrderResponseItemList(info.getOrderItemList()))
        .build();
  }

  private ZOrderDto.ShippingAddress toResponseShippingAddress(ZOrderCommand.CreateCartOrderInfo info) {
    return ZOrderDto.ShippingAddress.builder()
        .zipCode(info.getShippingAddress().getZipCode())
        .address(info.getShippingAddress().getAddress())
        .addressDetail(info.getShippingAddress().getAddressDetail())
        .build();
  }

  private List<ZOrderDto.CreateCartOrderResponse.Item> toCreateCartOrderResponseItemList(List<ZOrderCommand.CreateCartOrderInfo.Item> infoItemList) {
    return infoItemList.stream()
        .map(this::toResponseItem)
        .collect(Collectors.toUnmodifiableList());
  }

  private ZOrderDto.CreateCartOrderResponse.Item toResponseItem(ZOrderCommand.CreateCartOrderInfo.Item infoItem) {
    return ZOrderDto.CreateCartOrderResponse.Item.builder()
        .orderItemId(infoItem.getOrderItemId())
        .productId(infoItem.getProductId())
        .productName(infoItem.getProductName())
        .productPrice(infoItem.getProductPrice())
        .productQuantity(infoItem.getProductQuantity())
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
        .shippingAddress(toResponseShippingAddress(info))
        .totalPrice(info.getTotalPrice())
        .orderItemList(toCreateOrderResponseItemList(info.getOrderItemList()))
        .build();
  }

  private ZOrderDto.ShippingAddress toResponseShippingAddress(ZOrderCommand.CreateOrderInfo info) {
    return ZOrderDto.ShippingAddress.builder()
        .zipCode(info.getShippingAddress().getZipCode())
        .address(info.getShippingAddress().getAddress())
        .addressDetail(info.getShippingAddress().getAddressDetail())
        .build();
  }

  private List<ZOrderDto.CreateOrderResponse.Item> toCreateOrderResponseItemList(List<ZOrderCommand.CreateOrderInfo.Item> infoItemList) {
    return infoItemList.stream()
        .map(this::toResponseItem)
        .collect(Collectors.toUnmodifiableList());
  }

  private ZOrderDto.CreateOrderResponse.Item toResponseItem(ZOrderCommand.CreateOrderInfo.Item infoItem) {
    return ZOrderDto.CreateOrderResponse.Item.builder()
        .orderItemId(infoItem.getOrderItemId())
        .productId(infoItem.getProductId())
        .productName(infoItem.getProductName())
        .productPrice(infoItem.getProductPrice())
        .productQuantity(infoItem.getProductQuantity())
        .build();
  }

}

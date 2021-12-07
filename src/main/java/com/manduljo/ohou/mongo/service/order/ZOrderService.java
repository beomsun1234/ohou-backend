package com.manduljo.ohou.mongo.service.order;

import com.manduljo.ohou.mongo.domain.order.ZOrder;
import com.manduljo.ohou.mongo.domain.order.ZOrderItem;
import com.manduljo.ohou.mongo.domain.order.ZShippingAddress;
import com.manduljo.ohou.mongo.domain.product.ZProduct;
import com.manduljo.ohou.mongo.repository.member.ZMemberRepository;
import com.manduljo.ohou.mongo.repository.member.ZMemberTemplateRepository;
import com.manduljo.ohou.mongo.repository.order.ZOrderRepository;
import com.manduljo.ohou.mongo.repository.order.ZOrderTemplateRepository;
import com.manduljo.ohou.mongo.repository.product.ZProductRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ZOrderService {

  private final ZMemberRepository memberRepository;
  private final ZMemberTemplateRepository memberTemplateRepository;

  private final ZProductRepository productRepository;

  private final ZOrderRepository orderRepository;
  private final ZOrderTemplateRepository orderTemplateRepository;


  public ZOrderCommand.CreateCartOrderInfo createCartOrder(ZOrderCommand.CreateCartOrderCommand command) {
    // cartItemIdList 로 카트 아이템 정보 조회
    // 카트 아이템 정보로부터 productIdList 를 구해 상품 정보 조회
    // 카트 아이템 정보(상품 수량)와 상품 정보로 ZOrderItem 리스트 생성
    // command 와 ZOrderItem 리스트로 ZOrder 생성 및 저장
    return null;
  }

  public ZOrderCommand.CreateOrderInfo createOrder(ZOrderCommand.CreateOrderCommand command) {
    ZProduct product = productRepository.findById(command.getProductId()).orElseThrow();

    if (command.getPrice() != product.getPrice()) {
      throw new RuntimeException("상품 가격이 일치하지 않습니다.");
    }

    ZOrderItem orderItem = toInfoItem(command, product);

    ZOrder order = toOrder(command, orderItem);

    return toInfo(orderRepository.save(order));
  }

  private ZOrder toOrder(ZOrderCommand.CreateOrderCommand command, ZOrderItem orderItem) {
    return ZOrder.builder()
        .id(new ObjectId().toHexString())
        .memberId(command.getMemberId())
        .shippingAddress(toShippingAddress(command))
        .totalPrice(orderItem.getProductPrice() * orderItem.getProductQuantity())
        .orderItemList(List.of(orderItem))
        .build();
  }

  private ZShippingAddress toShippingAddress(ZOrderCommand.CreateOrderCommand command) {
    return ZShippingAddress.builder()
        .zipCode(command.getShippingAddress().getZipCode())
        .address(command.getShippingAddress().getAddress())
        .addressDetail(command.getShippingAddress().getAddressDetail())
        .build();
  }

  private ZOrderItem toInfoItem(ZOrderCommand.CreateOrderCommand command, ZProduct product) {
    return ZOrderItem.builder()
        .id(new ObjectId().toHexString())
        .productId(product.getId())
        .productName(product.getProductName())
        .productPrice(product.getPrice())
        .productQuantity(command.getQuantity())
        .build();
  }

  private ZOrderCommand.CreateOrderInfo toInfo(ZOrder order) {
    return ZOrderCommand.CreateOrderInfo.builder()
        .orderId(order.getId())
        .memberId(order.getMemberId())
        .shippingAddress(toShippingAddress(order))
        .totalPrice(order.getTotalPrice())
        .orderItemList(toInfoItemList(order.getOrderItemList()))
        .build();
  }

  private ZOrderCommand.ShippingAddress toShippingAddress(ZOrder order) {
    return ZOrderCommand.ShippingAddress.builder()
        .zipCode(order.getShippingAddress().getZipCode())
        .address(order.getShippingAddress().getAddress())
        .addressDetail(order.getShippingAddress().getAddressDetail())
        .build();
  }

  private List<ZOrderCommand.CreateOrderInfo.Item> toInfoItemList(List<ZOrderItem> orderItemList) {
    return orderItemList.stream()
        .map(this::toInfoItem)
        .collect(Collectors.toUnmodifiableList());
  }

  private ZOrderCommand.CreateOrderInfo.Item toInfoItem(ZOrderItem orderItem) {
    return ZOrderCommand.CreateOrderInfo.Item.builder()
        .orderItemId(orderItem.getId())
        .productId(orderItem.getProductId())
        .productName(orderItem.getProductName())
        .productPrice(orderItem.getProductPrice())
        .productQuantity(orderItem.getProductQuantity())
        .build();
  }

}

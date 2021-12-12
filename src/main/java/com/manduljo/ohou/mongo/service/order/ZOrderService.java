package com.manduljo.ohou.mongo.service.order;

import com.manduljo.ohou.mongo.domain.member.ZCartItem;
import com.manduljo.ohou.mongo.domain.member.ZMember;
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

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    ZMember member = memberRepository.findById(command.getMemberId()).orElseThrow();

    Set<String> cartItemIdSet = new HashSet<>(command.getCartItemIdList().size());
    cartItemIdSet.addAll(command.getCartItemIdList());

    List<ZCartItem> cartItemList = member.getCartItemList().stream()
        .filter(cartItem -> cartItemIdSet.contains(cartItem.getId()))
        .collect(Collectors.toUnmodifiableList());
    if (cartItemIdSet.size() != cartItemList.size()) {
      throw new RuntimeException("카트 아이템 데이터가 유효하지 않습니다.");
    }

    List<String> productIdList = cartItemList.stream()
        .map(ZCartItem::getProductId)
        .distinct()
        .collect(Collectors.toUnmodifiableList());
    List<ZProduct> productList = productRepository.findByIdIn(productIdList);

    Map<String, Integer> productQuantityMap = cartItemList.stream()
        .collect(Collectors.toUnmodifiableMap(ZCartItem::getProductId, ZCartItem::getProductQuantity, Integer::sum));
    if (productQuantityMap.size() != productList.size()) {
      throw new RuntimeException("카트 아이템 상품 데이터가 유효하지 않습니다.");
    }

    List<ZOrderItem> orderItem = toOrderItemList(productList, productQuantityMap);

    ZOrder order = toOrder(command, orderItem);
    ZOrder savedOrder = orderRepository.save(order);

    memberTemplateRepository.pullCartItemIn(command.getMemberId(), cartItemIdSet);

    return toCreateCartOrderInfo(savedOrder);
  }

  private List<ZOrderItem> toOrderItemList(List<ZProduct> productList, Map<String, Integer> productQuantityMap) {
    return productList.stream()
        .map(product -> toOrderItem(product, productQuantityMap.get(product.getId())))
        .collect(Collectors.toUnmodifiableList());
  }

  private ZOrderItem toOrderItem(ZProduct product, Integer productQuantity) {
    return ZOrderItem.builder()
        .id(new ObjectId().toHexString())
        .productId(product.getId())
        .productName(product.getName())
        .productPrice(product.getPrice())
        .productQuantity(productQuantity)
        .build();
  }

  private ZOrder toOrder(ZOrderCommand.CreateCartOrderCommand command, List<ZOrderItem> orderItemList) {
    int totalPrice = orderItemList.stream()
        .mapToInt(orderItem -> orderItem.getProductPrice() * orderItem.getProductQuantity())
        .sum();
    return ZOrder.builder()
        .id(new ObjectId().toHexString())
        .memberId(command.getMemberId())
        .shippingAddress(toShippingAddress(command))
        .totalPrice(totalPrice)
        .orderItemList(orderItemList)
        .build();
  }

  private ZShippingAddress toShippingAddress(ZOrderCommand.CreateCartOrderCommand command) {
    return ZShippingAddress.builder()
        .zipCode(command.getShippingAddress().getZipCode())
        .address(command.getShippingAddress().getAddress())
        .addressDetail(command.getShippingAddress().getAddressDetail())
        .build();
  }

  private ZOrderCommand.CreateCartOrderInfo toCreateCartOrderInfo(ZOrder order) {
    return ZOrderCommand.CreateCartOrderInfo.builder()
        .orderId(order.getId())
        .memberId(order.getMemberId())
        .shippingAddress(toCommandShippingAddress(order))
        .totalPrice(order.getTotalPrice())
        .orderItemList(toCreateCartOrderInfoItemList(order.getOrderItemList()))
        .build();
  }

  private ZOrderCommand.ShippingAddress toCommandShippingAddress(ZOrder order) {
    return ZOrderCommand.ShippingAddress.builder()
        .zipCode(order.getShippingAddress().getZipCode())
        .address(order.getShippingAddress().getAddress())
        .addressDetail(order.getShippingAddress().getAddressDetail())
        .build();
  }

  private List<ZOrderCommand.CreateCartOrderInfo.Item> toCreateCartOrderInfoItemList(List<ZOrderItem> orderItemList) {
    return orderItemList.stream()
        .map(this::toCreateCartOrderInfoItem)
        .collect(Collectors.toUnmodifiableList());
  }

  private ZOrderCommand.CreateCartOrderInfo.Item toCreateCartOrderInfoItem(ZOrderItem orderItem) {
    return ZOrderCommand.CreateCartOrderInfo.Item.builder()
        .orderItemId(orderItem.getId())
        .productId(orderItem.getProductId())
        .productName(orderItem.getProductName())
        .productPrice(orderItem.getProductPrice())
        .productQuantity(orderItem.getProductQuantity())
        .build();
  }

  public ZOrderCommand.CreateOrderInfo createOrder(ZOrderCommand.CreateOrderCommand command) {
    ZProduct product = productRepository.findById(command.getProductId()).orElseThrow();

    if (command.getPrice() != product.getPrice()) {
      throw new RuntimeException("상품 가격이 일치하지 않습니다.");
    }

    ZOrderItem orderItem = toOrderItem(command, product);

    ZOrder order = toOrder(command, orderItem);

    return toCreateOrderInfo(orderRepository.save(order));
  }

  private ZOrderItem toOrderItem(ZOrderCommand.CreateOrderCommand command, ZProduct product) {
    return ZOrderItem.builder()
        .id(new ObjectId().toHexString())
        .productId(product.getId())
        .productName(product.getName())
        .productPrice(product.getPrice())
        .productQuantity(command.getQuantity())
        .build();
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

  private ZOrderCommand.CreateOrderInfo toCreateOrderInfo(ZOrder order) {
    return ZOrderCommand.CreateOrderInfo.builder()
        .orderId(order.getId())
        .memberId(order.getMemberId())
        .shippingAddress(toCommandShippingAddress(order))
        .totalPrice(order.getTotalPrice())
        .orderItemList(toCreateOrderInfoItemList(order.getOrderItemList()))
        .build();
  }

  private List<ZOrderCommand.CreateOrderInfo.Item> toCreateOrderInfoItemList(List<ZOrderItem> orderItemList) {
    return orderItemList.stream()
        .map(this::toCreateOrderInfoItem)
        .collect(Collectors.toUnmodifiableList());
  }

  private ZOrderCommand.CreateOrderInfo.Item toCreateOrderInfoItem(ZOrderItem orderItem) {
    return ZOrderCommand.CreateOrderInfo.Item.builder()
        .orderItemId(orderItem.getId())
        .productId(orderItem.getProductId())
        .productName(orderItem.getProductName())
        .productPrice(orderItem.getProductPrice())
        .productQuantity(orderItem.getProductQuantity())
        .build();
  }

}

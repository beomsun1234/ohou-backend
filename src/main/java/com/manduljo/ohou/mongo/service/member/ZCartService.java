package com.manduljo.ohou.mongo.service.member;

import com.manduljo.ohou.mongo.domain.member.ZCartItem;
import com.manduljo.ohou.mongo.domain.member.ZMember;
import com.manduljo.ohou.mongo.domain.product.ZProduct;
import com.manduljo.ohou.mongo.repository.member.ZMemberRepository;
import com.manduljo.ohou.mongo.repository.member.ZMemberTemplateRepository;
import com.manduljo.ohou.mongo.repository.product.ZProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ZCartService {

  private final ZMemberRepository memberRepository;
  private final ZMemberTemplateRepository memberTemplateRepository;

  private final ZProductRepository productRepository;

  public ZCartCriteria.FindCartItemByMemberIdInfo findCartItemByMemberId(String memberId) {
    ZMember member = memberRepository.findById(memberId).orElseThrow();
    List<ZCartItem> cartItemList = member.getCartItemList();

    List<String> productIdList = cartItemList.stream()
        .map(ZCartItem::getProductId)
        .distinct()
        .collect(Collectors.toUnmodifiableList());
    List<ZProduct> productList = productRepository.findByIdIn(productIdList);
    Map<String, ZProduct> productMap = productList.stream().collect(Collectors.toUnmodifiableMap(ZProduct::getId, product -> product));

    return toInfo(cartItemList, productMap);
  }

  private ZCartCriteria.FindCartItemByMemberIdInfo toInfo(List<ZCartItem> cartItemList, Map<String, ZProduct> productMap) {
    List<ZCartCriteria.FindCartItemByMemberIdInfo.Item> infoItemList = cartItemList.stream()
        .map(cartItem -> toInfoItem(cartItem, productMap.get(cartItem.getProductId())))
        .collect(Collectors.toUnmodifiableList());

    int totalPrice = infoItemList.stream().mapToInt(ZCartCriteria.FindCartItemByMemberIdInfo.Item::getTotalPrice).sum();

    return ZCartCriteria.FindCartItemByMemberIdInfo.builder()
        .totalPrice(totalPrice)
        .cartItemList(infoItemList)
        .build();
  }

  private ZCartCriteria.FindCartItemByMemberIdInfo.Item toInfoItem(ZCartItem cartItem, ZProduct product) {
    return ZCartCriteria.FindCartItemByMemberIdInfo.Item.builder()
        .cartItemId(cartItem.getId())
        .productId(cartItem.getProductId())
        .productName(product.getProductName())
        .thumbnailImage(product.getThumbnailImage())
        .price(product.getPrice())
        .quantity(cartItem.getProductQuantity())
        .totalPrice(product.getPrice() * cartItem.getProductQuantity())
        .build();
  }

  public String addCartItem(ZCartCommand.AddCartItemCommand command) {
    String cartItemId = memberTemplateRepository.addCartItem(command);
    return cartItemId == null ? memberTemplateRepository.updateCartItem(command) : cartItemId;
  }

}

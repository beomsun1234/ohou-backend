package com.manduljo.ohou.mongo.repository.member;

import com.manduljo.ohou.mongo.domain.member.ZCartItem;
import com.manduljo.ohou.mongo.domain.member.ZMember;
import com.manduljo.ohou.mongo.service.cart.ZCartCommand;
import com.manduljo.ohou.mongo.service.member.ZMemberCriteria;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ZMemberTemplateRepository {

  private final MongoTemplate mongoTemplate;

  public List<ZMember> findAll(ZMemberCriteria.FindByIdCriteria criteria) {
    Query query = new Query();
    nameIs(query, criteria.getName());

    return query.getQueryObject().isEmpty() ?
        mongoTemplate.findAll(ZMember.class) :
        mongoTemplate.find(query, ZMember.class);
  }

  public ZMember findById(String id) {
    return mongoTemplate.findById(id, ZMember.class);
  }

  private void nameIs(Query query, String name) {
    if (StringUtils.hasText(name)) {
      query.addCriteria(Criteria.where("name").is(name));
    }
  }

  public ZMember save(ZMember member) {
    return mongoTemplate.save(member);
  }

  public String createCartItem(ZCartCommand.CreateCartItemCommand command) {
    ZCartItem cartItem = toCartItem(command);
    UpdateResult result = mongoTemplate.update(ZMember.class)
        .matching(Criteria.where("_id").is(new ObjectId(command.getMemberId())).and("cart_item_list.product_id").ne(new ObjectId(command.getProductId())))
        .apply(new Update().push("cart_item_list", cartItem))
        .first();
    return result.getModifiedCount() > 0 ? cartItem.getId() : null;
  }

  private ZCartItem toCartItem(ZCartCommand.CreateCartItemCommand command) {
    return ZCartItem.builder()
        .id(new ObjectId().toHexString())
        .productId(command.getProductId())
        .productQuantity(command.getProductQuantity())
        .build();
  }

  public String updateCartItemProductQuantity(ZCartCommand.CreateCartItemCommand command) {
    ZMember member = mongoTemplate.findAndModify(
        Query.query(Criteria.where("_id").is(new ObjectId(command.getMemberId())).and("cart_item_list.product_id").is(new ObjectId(command.getProductId()))),
        new Update().inc("cart_item_list.$.product_quantity", command.getProductQuantity()),
        ZMember.class
    );
    ZCartItem updatedCartItem = Objects.requireNonNull(member).getCartItemList().stream()
        .filter(cartItem -> cartItem.getProductId().equals(command.getProductId()))
        .findFirst()
        .orElse(null);
    return updatedCartItem == null ? null : updatedCartItem.getId();
  }

  public String updateCartItemProductQuantity(ZCartCommand.UpdateCartItemProductQuantityCommand command) {
    ZMember member = mongoTemplate.findAndModify(
        Query.query(Criteria.where("cart_item_list._id").is(new ObjectId(command.getCartItemId())).and("_id").is(new ObjectId(command.getMemberId()))),
        new Update().set("cart_item_list.$.product_quantity", command.getProductQuantity()),
        ZMember.class
    );
    ZCartItem updatedCartItem = Objects.requireNonNull(member).getCartItemList().stream()
        .filter(cartItem -> cartItem.getId().equals(command.getCartItemId()))
        .findFirst()
        .orElse(null);
    return updatedCartItem == null ? null : updatedCartItem.getId();
  }

}

package com.manduljo.ohou.mongo.repository.order;

import com.manduljo.ohou.mongo.domain.order.ZOrder;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ZOrderRepository extends MongoRepository<ZOrder, String> {
}

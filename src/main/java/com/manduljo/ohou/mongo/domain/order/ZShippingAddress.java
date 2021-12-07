package com.manduljo.ohou.mongo.domain.order;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ZShippingAddress {

  private String zipCode;

  private String address;

  private String addressDetail;

}

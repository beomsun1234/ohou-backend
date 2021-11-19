package com.manduljo.ohou.domain.orders;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor
public class ShippingAddress {

    private String zipCode;

    private String address;

    private String addressDetail;

    @Builder
    public ShippingAddress(String zipCode, String address, String addressDetail) {
        this.zipCode = zipCode;
        this.address = address;
        this.addressDetail = addressDetail;
    }
}

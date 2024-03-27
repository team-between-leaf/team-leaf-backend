package com.team.leaf.user.account.dto.common;

import lombok.Data;

@Data
public class ShippingAddressReq {

    private String recipient;

    private String phone;

    private String address;

    private String detailedAddress;

    private Boolean defaultAddress;

}
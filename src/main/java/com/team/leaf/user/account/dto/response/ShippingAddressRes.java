package com.team.leaf.user.account.dto.response;

import com.team.leaf.user.account.entity.ShippingAddress;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShippingAddressRes {

    private Long addressId;

    private String recipient;

    private String phone;

    private String address;

    private String detailedAddress;

    private Boolean defaultAddress;

    public ShippingAddressRes(ShippingAddress shippingAddress) {
        this.addressId = shippingAddress.getAddressId();
        this.recipient = shippingAddress.getRecipient();
        this.phone = shippingAddress.getPhone();
        this.address = shippingAddress.getAddress();
        this.detailedAddress = shippingAddress.getDetailedAddress();
        this.defaultAddress = shippingAddress.getDefaultAddress();
    }

}

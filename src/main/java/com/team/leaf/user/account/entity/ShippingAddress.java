package com.team.leaf.user.account.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addressId;

    private String recipient;

    private String phone;

    private String address;

    private String detailedAddress;

    private Boolean defaultAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", insertable = false, updatable = false)
    AccountDetail accountDetail;

    @Column(name = "account_id")
    private Long accountId;

    public ShippingAddress(Long accountId) {
        this.accountId = accountId;
    }

    public void setAccountDetail(AccountDetail accountDetail) {
        this.accountDetail = accountDetail;
    }

    public void updateShippingAddress(String recipient, String phone, String address, String detailedAddress, Boolean defaultAddress) {
        this.recipient = recipient;
        this.phone = phone;
        this.address = address;
        this.detailedAddress = detailedAddress;
        this.defaultAddress = defaultAddress;
    }

}
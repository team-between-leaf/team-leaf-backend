package com.team.leaf.shopping.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class OrderPaymentDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderDetailId;

    private String cardInfo;

    private LocalDateTime paymentRequest;

    private LocalDateTime paymentApproval;

}

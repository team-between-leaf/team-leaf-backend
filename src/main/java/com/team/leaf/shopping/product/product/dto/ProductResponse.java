package com.team.leaf.shopping.product.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProductResponse {

    private long productId;

    private String title;

    private String description;

    private int price;

    private String image;

    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:MM:SS" , timezone = "Asia/Seoul")
    private LocalDateTime registrationDate;

    private long views;

    private double discountRate;

    private double averageRating;

}

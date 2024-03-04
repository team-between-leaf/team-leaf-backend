package com.team.leaf.shopping.product.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@ToString
@Getter
public class ProductDetailResponse {

    private long productId;

    private String title;

    private String description;

    private int price;

    private String image;

    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:MM:SS" , timezone = "Asia/Seoul")
    private LocalDateTime registrationDate;

    private long saleRate;

    private long views;

    private double discountRate;

    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd" , timezone = "Asia/Seoul")
    private LocalDate deliveryStart;

    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd" , timezone = "Asia/Seoul")
    private LocalDate productionTime;

    private List<OptionResponse> options;

    public ProductDetailResponse(long productId, String title, String description, int price, String image, LocalDateTime registrationDate, long saleRate, long views, double discountRate, LocalDate deliveryStart, LocalDate productionTime) {
        this.productId = productId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
        this.registrationDate = registrationDate;
        this.saleRate = saleRate;
        this.views = views;
        this.discountRate = discountRate;
        this.deliveryStart = deliveryStart;
        this.productionTime = productionTime;
    }

    public void setOption(List<OptionResponse> optionList) {
        this.options = optionList;
    }
}

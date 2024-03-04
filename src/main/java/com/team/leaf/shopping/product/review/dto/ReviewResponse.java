package com.team.leaf.shopping.product.review.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReviewResponse {

    private long reviewId;

    private long score;

    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:MM:SS" , timezone = "Asia/Seoul")
    private LocalDateTime reviewDate;

    private String buyerNickname;

}

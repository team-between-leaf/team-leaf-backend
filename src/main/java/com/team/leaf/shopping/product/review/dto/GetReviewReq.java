package com.team.leaf.shopping.product.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetReviewReq {

    private List<GetReviewRes> review;

}

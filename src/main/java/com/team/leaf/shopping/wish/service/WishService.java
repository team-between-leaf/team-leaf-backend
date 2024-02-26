package com.team.leaf.shopping.wish.service;

import com.team.leaf.shopping.product.product.entity.Product;
import com.team.leaf.shopping.wish.dto.WishResponse;
import com.team.leaf.shopping.wish.entity.Wish;
import com.team.leaf.shopping.wish.repository.WishRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WishService {
    private final WishRepository wishRepository;
    public List<WishResponse> findWishById(Long userId){
        List<Wish> wishes = wishRepository.findAllByUserUserId(userId);
        List<WishResponse> wishResponses = new ArrayList<>();
            for(Wish wish : wishes){
                Product product = wish.getProduct();
                WishResponse wishResponse = WishResponse.builder()
                        .title(product.getTitle())
                        .price(product.getPrice())
                        .image(product.getImage())
                        .discountRate(product.getDiscountRate())
                        .registrationDate(product.getRegistrationDate())
                        .views(product.getViews())
                        .reviews(product.getReviews().size())
                        .build();
                wishResponses.add(wishResponse);
            }
        return wishResponses;
    }

    @Transactional
    public Boolean deleteById(long userId, long productId){
        return (wishRepository.deleteByUserIdAndProductId(userId, productId))>0;
    }
}
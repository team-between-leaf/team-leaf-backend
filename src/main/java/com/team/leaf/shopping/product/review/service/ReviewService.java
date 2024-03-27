package com.team.leaf.shopping.product.review.service;

import com.team.leaf.shopping.product.product.entity.Product;
import com.team.leaf.shopping.product.product.repository.ProductRepository;
import com.team.leaf.shopping.product.review.dto.GetReviewRes;
import com.team.leaf.shopping.product.review.dto.ModifyReviewReq;
import com.team.leaf.shopping.product.review.dto.PostReviewReq;
import com.team.leaf.shopping.product.review.dto.ReviewResponse;
import com.team.leaf.shopping.product.review.entity.Review;
import com.team.leaf.shopping.product.review.repository.ReviewRepository;
import com.team.leaf.user.account.entity.AccountDetail;
import com.team.leaf.user.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final AccountRepository accountRepository;
    private final ProductRepository productRepository;

    public List<ReviewResponse> findReviewByProductId(long productId) {
        return reviewRepository.findReviewByProductId(productId);
    }

    public List<ReviewResponse> findReviewByUserId(long userId) {
        return reviewRepository.findReviewByUserId(userId);
    }

    public String postReview(AccountDetail account, PostReviewReq request) {
        AccountDetail accountDetail1 = accountRepository.findById(account.getUserId())
                .orElseThrow(()  -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("제품을 찾을 수 없습니다."));

        Review review = Review.builder()
                .writer(accountDetail1)
                .product(product)
                .content(request.getContent())
                .score(request.getScore())
                .deliveryRating(request.getDeliveryRating())
                .packagingRating(request.getPackagingRating())
                .qualityRating(request.getQualityRating())
                .build();

        reviewRepository.save(review);

        return "Success post review";
    }

    public String modifyReview(AccountDetail account, ModifyReviewReq request){
        Review review = reviewRepository.findById(request.getReviewId()).orElse(null);

        if(review == null) {
            throw new RuntimeException("리뷰를 찾을 수 없습니다.");
        }

        AccountDetail accountDetail1 = accountRepository.findById(account.getUserId())
                .orElseThrow(()  -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if(accountDetail1.getUserId() != review.getWriter().getUserId()) {
            throw new RuntimeException("본인이 작성한 리뷰만 수정할 수 있습니다.");
        } else {
            Integer score = review.getScore();

            if(score != null) {
                review.setScore(request.getScore());
            }
            if(request.getContent() != null && !request.getContent().isEmpty()) {
                review.setContent(request.getContent());
            }
            if(request.getDeliveryRating() != null) {
                review.setDeliveryRating(request.getDeliveryRating());
            }
            if(request.getPackagingRating() != null) {
                review.setPackagingRating(request.getPackagingRating());
            }
            if(request.getQualityRating() != null) {
                review.setQualityRating(request.getQualityRating());
            }

            reviewRepository.save(review);
        }

        return "Success modify review";
    }

    public String deleteReview(AccountDetail account, Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);

        if(review == null) {
            throw new RuntimeException("리뷰를 찾을 수 없습니다.");
        }

        AccountDetail accountDetail1 = accountRepository.findById(account.getUserId())
                .orElseThrow(()  -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if(accountDetail1.getUserId() != review.getWriter().getUserId()) {
            throw new RuntimeException("본인이 작성한 리뷰만 삭제할 수 있습니다.");
        } else {
            reviewRepository.deleteById(review.getReviewId());
        }

        return "Success delete review";
    }

    public List<GetReviewRes> getReviewsById(AccountDetail account) {
        AccountDetail accountDetail1 = accountRepository.findById(account.getUserId())
                .orElseThrow(()  -> new RuntimeException("사용자를 찾을 수 없습니다."));

        List<Review> reviews = reviewRepository.findByWriterUserId(accountDetail1.getUserId());
        List<GetReviewRes> getReviewRes = reviews.stream()
                .map(review -> {

                    return new GetReviewRes(
                            review.getReviewId(), review.getWriter().getNickname(), review.getContent(), review.getScore(),
                            review.getDeliveryRating(), review.getPackagingRating(), review.getQualityRating(), review.getReviewDate());
                })
                .collect(Collectors.toList());

        return getReviewRes;

    }

}

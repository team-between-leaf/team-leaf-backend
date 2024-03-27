package com.team.leaf.shopping.product.review.repository;

import com.team.leaf.shopping.product.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> , CustomReviewRepository {

    List<Review> findByWriterUserId(long userId);

}


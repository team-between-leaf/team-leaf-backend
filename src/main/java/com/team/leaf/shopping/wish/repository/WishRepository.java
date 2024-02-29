package com.team.leaf.shopping.wish.repository;

import com.team.leaf.shopping.wish.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Long> {
    int deleteByUserUserIdAndProductProductId(Long userId, Long productId);

    List<Wish> findAllByUserUserId(Long userId);

    boolean existsByUserUserIdAndProductProductId(Long userId, Long productId);
}

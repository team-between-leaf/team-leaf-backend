package com.team.leaf.shopping.wish.repository;

import com.team.leaf.shopping.wish.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
public interface WishRepository extends JpaRepository<Wish, Long> {
    @Modifying
    @Query("DELETE FROM Wish w WHERE w.user.id = :userId AND w.product.id = :productId")
    int deleteByUserIdAndProductId(@Param("userId") Long userId,@Param("productId") Long productId);

    List<Wish> findAllByUserUserId(Long userId);
}
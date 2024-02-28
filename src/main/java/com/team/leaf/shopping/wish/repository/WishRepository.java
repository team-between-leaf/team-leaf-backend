package com.team.leaf.shopping.wish.repository;

import com.team.leaf.shopping.wish.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish, Long> {
}

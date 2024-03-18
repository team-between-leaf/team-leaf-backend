package com.team.leaf.user.account.repository;

import com.team.leaf.user.account.entity.InterestCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestCategoryRepository extends JpaRepository<InterestCategory, Long> {
}


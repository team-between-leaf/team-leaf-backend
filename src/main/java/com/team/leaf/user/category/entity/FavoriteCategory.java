package com.team.leaf.user.category.entity;

import com.team.leaf.shopping.product.category.entity.Category;
import com.team.leaf.user.account.entity.AccountDetail;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class FavoriteCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long favoriteCategoryId;

    @OneToMany
    private List<AccountDetail> users;

    @OneToMany
    private List<Category> categories;

}

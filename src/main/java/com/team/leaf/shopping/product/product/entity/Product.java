package com.team.leaf.shopping.product.product.entity;

import com.team.leaf.shopping.product.category.entity.CategoryProduct;
import com.team.leaf.shopping.product.review.entity.Review;
import com.team.leaf.user.account.entity.AccountDetail;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;

    private String title;

    private String description;

    private int price;

    private String image;

    private LocalDateTime registrationDate;

    private long views;

    private long saleRate;

    private double discountRate;

    private LocalDate deliveryStart;

    private LocalDate productionTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private AccountDetail seller;

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Review> reviews = new LinkedList<>();

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<CategoryProduct> categories = new LinkedList<>();

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<ProductOption> productOptions = new LinkedList<>();

    public void addProductOption(ProductOption option) {
        productOptions.add(option);
        option.setProduct(this);
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }
}

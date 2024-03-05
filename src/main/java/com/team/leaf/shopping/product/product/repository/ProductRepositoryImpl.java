package com.team.leaf.shopping.product.product.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.leaf.shopping.product.product.dto.OptionResponse;
import com.team.leaf.shopping.product.product.dto.ProductDetailResponse;
import com.team.leaf.shopping.product.product.dto.ProductRequest;
import com.team.leaf.shopping.product.product.dto.ProductResponse;
import com.team.leaf.shopping.product.product.entity.Product;
import com.team.leaf.user.account.entity.AccountDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.team.leaf.shopping.product.product.entity.QProduct.product;
import static com.team.leaf.shopping.product.product.entity.QProductOption.productOption;
import static com.team.leaf.shopping.product.review.entity.QReview.review;
import static com.team.leaf.user.account.entity.QAccountDetail.accountDetail;
import static com.team.leaf.user.account.entity.QAccountPrivacy.accountPrivacy;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements CustomProductRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ProductResponse> getAllProduct(Pageable pageable, ProductRequest request) {
        return jpaQueryFactory.select(Projections.constructor(ProductResponse.class,
                        product.productId,
                        product.title,
                        product.description,
                        product.price,
                        product.image,
                        product.registrationDate,
                        product.saleRate,
                        product.views,
                        product.discountRate,
                        review.score.avg()
                ))
                .from(product)
                .leftJoin(product.reviews, review).on(review.product.eq(product))
                .groupBy(product.productId)
                .orderBy(request.getSortType().getSort())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<ProductResponse> getAllProductBySearch(Pageable pageable, ProductRequest request, String search) {
        return jpaQueryFactory.select(Projections.constructor(ProductResponse.class,
                        product.productId,
                        product.title,
                        product.description,
                        product.price,
                        product.image,
                        product.registrationDate,
                        product.saleRate,
                        product.views,
                        product.discountRate,
                        review.score.avg()
                ))
                .from(product)
                .leftJoin(product.reviews, review).on(review.product.eq(product))
                .groupBy(product.productId)
                .orderBy(request.getSortType().getSort())
                .where(product.title.contains(search))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public Optional<ProductDetailResponse> findProductByProductId(long productId) {
        ProductDetailResponse result = jpaQueryFactory.select(Projections.constructor(ProductDetailResponse.class,
                        product.productId,
                        product.title,
                        product.description,
                        product.price,
                        product.image,
                        product.registrationDate,
                        product.saleRate,
                        product.views,
                        product.discountRate,
                        product.deliveryStart,
                        product.productionTime,
                        accountDetail.userId,
                        accountDetail.nickname,
                        accountPrivacy.Image
                ))
                .from(product)
                .innerJoin(product.seller, accountDetail)
                .innerJoin(accountDetail.userDetail, accountPrivacy)
                .groupBy(product.productId)
                .where(product.productId.eq(productId))
                .fetchOne();

        List<OptionResponse> optionList = jpaQueryFactory.select(
                        Projections.constructor(
                                OptionResponse.class,
                                productOption.keyData,
                                productOption.valueData
                        ))
                .from(productOption)
                .innerJoin(productOption.product).on(product.productId.eq(productId))
                .fetch();


        result.setOption(optionList);

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<Product> findProductByProductIdAndSeller(long productId, AccountDetail account) {
        Product result = jpaQueryFactory.select(product)
                .from(product)
                .innerJoin(product.seller, accountDetail).on(accountDetail.eq(account))
                .where(product.productId.eq(productId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public List<ProductResponse> findSellerProductByUserId(Pageable pageable, ProductRequest request, long userId) {
        return jpaQueryFactory.select(Projections.constructor(ProductResponse.class,
                        product.productId,
                        product.title,
                        product.description,
                        product.price,
                        product.image,
                        product.registrationDate,
                        product.saleRate,
                        product.views,
                        product.discountRate,
                        review.score.avg()
                ))
                .from(product)
                .innerJoin(product.seller, accountDetail).on(accountDetail.userId.eq(userId))
                .leftJoin(product.reviews, review).on(review.product.eq(product))
                .groupBy(product.productId)
                .orderBy(request.getSortType().getSort())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}

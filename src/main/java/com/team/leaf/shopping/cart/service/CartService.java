package com.team.leaf.shopping.cart.service;

import com.team.leaf.shopping.cart.dto.CartProductCouponResponse;
import com.team.leaf.shopping.cart.dto.CartProductOptionResponse;
import com.team.leaf.shopping.cart.dto.CartProductResponse;
import com.team.leaf.shopping.cart.dto.CartResponse;
import com.team.leaf.shopping.cart.entity.Cart;
import com.team.leaf.shopping.cart.repository.CartRepository;
import com.team.leaf.shopping.coupon.entity.Coupon;
import com.team.leaf.shopping.product.product.entity.Product;
import com.team.leaf.shopping.product.product.entity.ProductOption;
import com.team.leaf.shopping.product.product.repository.ProductRepository;
import com.team.leaf.user.account.entity.AccountDetail;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    @Transactional
    public void addProductToCart(long productId, AccountDetail accountDetail) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("not found Product Data"));

        Cart cart = cartRepository.findCartByUserAndProduct(accountDetail, product)
                .orElseGet(() -> cartRepository.save(Cart.createCart(product, accountDetail)));

        cart.increaseAmount(1);
    }

    public CartResponse getCart(AccountDetail accountDetail){
        List<Cart> carts = cartRepository.findAllByUser(accountDetail)
                .orElseThrow(()-> new RuntimeException("Not Found Cart"));
        List<CartProductResponse> cartProductResponses = new ArrayList<>();
        for(Cart cart : carts){
            Product product = cart.getProduct();
            CartProductResponse cartProductResponse = CartProductResponse.builder()
                    .productOptionResponses(getProductOption(product))
                    .productCouponResponses(getProductCoupon(product))
                    .title(product.getTitle())
                    .image(product.getImage())
                    .price(product.getPrice())
                    .discountRate(product.getDiscountRate())
                    .amount(cart.getAmount())
                    .build();
            cartProductResponses.add(cartProductResponse);
        }
        return CartResponse.builder()
                .productResponseList(cartProductResponses)
                .shippingAddress(accountDetail.getShippingAddress())
                .build();
    }
    public List<CartProductOptionResponse> getProductOption(Product product){
        List<ProductOption> productOptions = product.getProductOptions();
        List<CartProductOptionResponse> cartProductOptionResponses = new ArrayList<>();
        for(ProductOption productOption : productOptions){
            CartProductOptionResponse cartProductOptionResponse = CartProductOptionResponse.builder()
                    .keyData(productOption.getKeyData())
                    .valueData(productOption.getValueData())
                    .build();
            cartProductOptionResponses.add(cartProductOptionResponse);
        }
        return cartProductOptionResponses;
    }

    public List<CartProductCouponResponse> getProductCoupon(Product product){
        List<Coupon> couponList = product.getCoupons();
        List<CartProductCouponResponse> cartProductCouponResponseList = new ArrayList<>();
        for(Coupon coupon : couponList){
            CartProductCouponResponse cartProductCouponResponse = CartProductCouponResponse.builder()
                    .couponName(coupon.getCouponName())
                    .saleRate(coupon.getSaleRate())
                    .build();
            cartProductCouponResponseList.add(cartProductCouponResponse);
        }
        return cartProductCouponResponseList;
    }
}

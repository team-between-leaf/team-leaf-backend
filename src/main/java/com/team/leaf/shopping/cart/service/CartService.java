package com.team.leaf.shopping.cart.service;

import com.team.leaf.shopping.cart.dto.*;
import com.team.leaf.shopping.cart.dto.request.CartProductOptionRequest;
import com.team.leaf.shopping.cart.dto.request.CartProductRequest;
import com.team.leaf.shopping.cart.entity.Cart;
import com.team.leaf.shopping.cart.entity.CartProductOption;
import com.team.leaf.shopping.cart.repository.CartProductOptionRepository;
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
    private final CartProductOptionRepository cartProductOptionRepository;

    @Transactional
    public void addProductToCart(long productId,
                                 CartProductRequest cartProductRequest,
                                 AccountDetail accountDetail) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("not found Product Data"));

        Cart cart = cartRepository.findCartByUserAndProduct(accountDetail, product)
                .orElseGet(() -> cartRepository.save(Cart.createCart(product, accountDetail)));

        for (int i = 0; i < cartProductRequest.getSelectKeyData().size(); i++) {
            String key = cartProductRequest.getSelectKeyData().get(i);
            String value = cartProductRequest.getSelectValueData().get(i);
            CartProductOption cartProductOption = CartProductOption.builder()
                    .selectKeyData(key)
                    .selectValueData(value)
                    .cart(cart)
                    .build();
            cartProductOptionRepository.save(cartProductOption);
        }
        cart.increaseAmount(1);
    }

    public CartResponse getCart(AccountDetail accountDetail) {
        List<Cart> carts = cartRepository.findAllByUser(accountDetail)
                .orElseThrow(() -> new RuntimeException("Not Found Cart"));
        List<CartProductResponse> cartProductResponses = new ArrayList<>();
        //List<String>[] OptionData = getSelectProductOption(carts);
        for (Cart cart : carts) {
            Product product = cart.getProduct();
            CartProductResponse cartProductResponse = CartProductResponse.builder()
                    .productOptionResponses(getProductOption(product))
                    .productCouponResponses(getProductCoupon(product))
                    .productSelectOptionResponses(getSelectProductOption(cart))
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

    public List<CartProductOptionResponse> getProductOption(Product product) {
        List<ProductOption> productOptions = product.getProductOptions();
        List<CartProductOptionResponse> cartProductOptionResponses = new ArrayList<>();
        for (ProductOption productOption : productOptions) {
            CartProductOptionResponse cartProductOptionResponse = CartProductOptionResponse.builder()
                    .keyData(productOption.getKeyData())
                    .valueData(productOption.getValueData())
                    .build();
            cartProductOptionResponses.add(cartProductOptionResponse);
        }
        return cartProductOptionResponses;
    }

    public List<CartProductCouponResponse> getProductCoupon(Product product) {
        List<Coupon> couponList = product.getCoupons();
        List<CartProductCouponResponse> cartProductCouponResponseList = new ArrayList<>();
        for (Coupon coupon : couponList) {
            CartProductCouponResponse cartProductCouponResponse = CartProductCouponResponse.builder()
                    .couponName(coupon.getCouponName())
                    .saleRate(coupon.getSaleRate())
                    .build();
            cartProductCouponResponseList.add(cartProductCouponResponse);
        }
        return cartProductCouponResponseList;
    }

    public List<CartProductSelectOptionResponse> getSelectProductOption(Cart carts) {
        List<CartProductOption> cartProductOption = carts.getCartProductOption();
        List<CartProductSelectOptionResponse> cartProductSelectOptionResponses = new ArrayList<>();
        for (CartProductOption cartProductOptions : cartProductOption) {
            CartProductSelectOptionResponse cartProductSelectOptionResponse = CartProductSelectOptionResponse.builder()
                    .selectKeyData(cartProductOptions.getSelectKeyData())
                    .selectValueData(cartProductOptions.getSelectValueData())
                    .build();
            cartProductSelectOptionResponses.add(cartProductSelectOptionResponse);
        }
        return cartProductSelectOptionResponses;
    }

    @Transactional
    public String updateCartOption(Long productId, CartProductOptionRequest cartProductOptionRequest) {
        Cart cart = cartRepository.findByProductProductId(productId)
                .orElseThrow(() -> new RuntimeException("Not Found Cart"));
        List<CartProductOption> cartProductOptions = cartProductOptionRepository.findAllByCart(cart)
                .orElseThrow(() -> new RuntimeException("Not Found Option"));
        List<CartProductOption> updatedOptions = new ArrayList<>();

        for (CartProductOption option : cartProductOptions) {
            if (option.getSelectKeyData().equals(cartProductOptionRequest.getSelectKeyData())) {
                option.updateOption(cartProductOptionRequest.getSelectValueData());
                updatedOptions.add(option);
            }
        }

        cartProductOptionRepository.saveAll(updatedOptions);

        return "Success Update";
    }

    @Transactional
    public String deleteCart(List<Long> productIds, AccountDetail accountDetail){
        for(Long productId : productIds){
            cartRepository.deleteByProductProductIdAndUser(productId,accountDetail);
        }
        return "Delete Success";
    }
}

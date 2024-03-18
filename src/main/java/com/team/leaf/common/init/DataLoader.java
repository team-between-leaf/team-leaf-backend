package com.team.leaf.common.init;

import com.team.leaf.shopping.cart.entity.Cart;
import com.team.leaf.shopping.cart.repository.CartRepository;
import com.team.leaf.shopping.coupon.entity.Coupon;
import com.team.leaf.shopping.coupon.repository.CouponRepository;
import com.team.leaf.shopping.follow.entity.Follow;
import com.team.leaf.shopping.follow.repository.FollowRepository;
import com.team.leaf.shopping.product.product.entity.Product;
import com.team.leaf.shopping.product.product.entity.ProductOption;
import com.team.leaf.shopping.product.product.repository.ProductRepository;
import com.team.leaf.shopping.product.review.entity.Review;
import com.team.leaf.shopping.search.util.Trie;
import com.team.leaf.shopping.wish.entity.Wish;
import com.team.leaf.shopping.wish.repository.WishRepository;
import com.team.leaf.user.account.config.JwtSecurityConfig;
import com.team.leaf.user.account.entity.AccountDetail;
import com.team.leaf.user.account.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;
    private final WishRepository wishRepository;
    private final CartRepository cartRepository;
    private final CouponRepository couponRepository;
    private final JwtSecurityConfig jwtSecurityConfig;
    private final FollowRepository followRepository;
    private final Trie trie;

    @Override
    @Transactional
    public void run(String... args) {
        // 트라이 초기 데이터 정렬
        trie.initTrie();

        // 초기 데이터 저장
        // Account
        AccountDetail accountDetail1 = accountRepository.findByEmail("hchaehyun@naver.com")
                .orElseGet(() -> accountRepository.save(AccountDetail.joinAccount(
                        "hchaehyun@naver.com",
                        jwtSecurityConfig.passwordEncoder().encode("password"),
                        "01012341234",
                        "hchaehyun@naver.com")));

        AccountDetail accountDetail2 = accountRepository.findByEmail("admin@admin.com")
                .orElseGet(() -> accountRepository.save(AccountDetail.joinAccount(
                        "admin@admin.com",
                        jwtSecurityConfig.passwordEncoder().encode("@Ll12345"),
                        "01043214312",
                        "admin@admin.com")));

        AccountDetail accountDetail3 = accountRepository.findByEmail("seller1234@naver.com")
                .orElseGet(() -> accountRepository.save(AccountDetail.joinAccount(
                        "seller1234@naver.com",
                        jwtSecurityConfig.passwordEncoder().encode("Qwer1234"),
                        "01046816563",
                        "Seller4512")));
        accountDetail3.getUserDetail().updateProfileImage("imageUrl");

        AccountDetail accountDetail4 = accountRepository.findByEmail("buyer1234@naver.com")
                .orElseGet(() -> accountRepository.save(AccountDetail.joinAccount(
                        "buyer1234@naver.com",
                        jwtSecurityConfig.passwordEncoder().encode("Qwer1234"),
                        "01051216598",
                        "buyer1234")));

        // Follow
        Follow follow1 = Follow.createFollow(accountDetail1 , accountDetail4);
        Follow follow2 = Follow.createFollow(accountDetail1 , accountDetail2);
        Follow follow3 = Follow.createFollow(accountDetail2 , accountDetail4);
        Follow follow4 = Follow.createFollow(accountDetail3 , accountDetail4);

        // Product
        Product product1 = Product.builder().title("딸기 케이크").description("10년 경력의 베이킹 전문가가 만든 딸기 케이크입니다.").price(18000).seller(accountDetail3).discountRate(2.2).image("https://ibb.co/k27RhH2").saleRate(10).views(0).registrationDate(LocalDateTime.of(2018, 3, 6, 12, 30, 00)).build();
        ProductOption color_option1 = ProductOption.builder().keyData("색상").valueData("blue").build();
        ProductOption color_option2 = ProductOption.builder().keyData("색상").valueData("red").build();
        ProductOption color_option3 = ProductOption.builder().keyData("색상").valueData("yellow").build();
        product1.addProductOption(color_option1);
        product1.addProductOption(color_option2);
        product1.addProductOption(color_option3);

        Product product2 = Product.builder().title("휴지").description("1576칸으로 구성된 두루마니 휴지입니다. 멸균이 잘 되어있는 아주 깨끗한 휴지").price(3000).seller(accountDetail3).discountRate(1.7).image("https://ibb.co/FDGnKLc").saleRate(5).views(0).registrationDate(LocalDateTime.of(2021, 5, 1, 11, 26, 00)).build();
        Product product3 = Product.builder().title("후라이팬").description("총알도 거뜬히 막는 후라이팬, 대충 강해보이는 후라이팬 그렇다구요").price(9000).seller(accountDetail3).discountRate(1.8).image("https://ibb.co/LJNsDs2").saleRate(20).views(0).registrationDate(LocalDateTime.of(2019, 8, 1, 5, 0, 00)).build();
        Product product4 = Product.builder().title("신라면").description("전 국민이 좋아하는 신 라면, 옛날보다 버섯을 덜 넣어주지만 그래도 맛있어요").price(800).seller(accountDetail3).discountRate(1.0).image("https://ibb.co/sqdvg78").saleRate(0).views(0).registrationDate(LocalDateTime.of(2018, 3, 6, 12, 30, 00)).build();
        Product product5 = Product.builder().title("면 봉").description("대충 깨끗하고 멸균처리한 면봉이라는 설명").price(9000).seller(accountDetail3).discountRate(4.5).image("https://ibb.co/Ptjpvbc").saleRate(10).views(0).registrationDate(LocalDateTime.of(2018, 3, 6, 12, 30, 00)).build();
        Product product6 = Product.builder().title("보조 배터리").description("500000maH 용량을 가진 보조 배터리, 참고로 자동차 배터리보다 용량이 크다. 아무도 인읽을 것 같으니 막 쓴다.").price(50000).seller(accountDetail3).discountRate(5.5).image("https://ibb.co/7vnG7ff").saleRate(30).views(0).registrationDate(LocalDateTime.of(2018, 3, 6, 12, 30, 00)).build();
        Product product7 = Product.builder().title("마스크").description("COVID-19를 막아주는 마스크입니다. 심지어 못생긴 하관도 막아주는 만능 마스크").price(2000).seller(accountDetail3).discountRate(1.9).image("https://ibb.co/0rXq2LK").saleRate(8).views(0).registrationDate(LocalDateTime.of(2018, 3, 6, 12, 30, 00)).build();
        Product product8 = Product.builder().title("손톱깎이").description("철도 자른다는 강력한 손톱깎이").price(5000).seller(accountDetail3).discountRate(5.2).image("https://ibb.co/VTLmWgH").saleRate(20).views(0).registrationDate(LocalDateTime.of(2024, 3, 6, 12, 30, 00)).build();
        Product product9 = Product.builder().title("흑채").description("흑채.,. 내가 죽을 때 가지 쓸일이 없길 바라는 마음").price(18000).seller(accountDetail3).discountRate(5.0).image("https://ibb.co/rFtJzHg").saleRate(20).views(0).registrationDate(LocalDateTime.of(2018, 3, 6, 12, 30, 00)).build();
        Product product10 = Product.builder().title("꽃병").description("꽃병이다. 꽃을 넣을 수 있는 병이다. 그렇다고").price(5000).seller(accountDetail3).discountRate(6.2).image("https://ibb.co/zRZj4bF").saleRate(10).views(0).registrationDate(LocalDateTime.of(2018, 3, 6, 12, 30, 00)).build();

        // Review
        Review review1 = Review.builder().reviewDate(LocalDateTime.of(2019 , 4 , 15 , 12 , 30 , 00)).score(4).content("리뷰 데이터 1").writer(accountDetail2).product(product1).orderType("orderType").build();
        Review review2 = Review.builder().reviewDate(LocalDateTime.of(2022 , 5 , 12 , 13 , 00 , 00)).score(2).content("리뷰 데이터 2").writer(accountDetail1).product(product1).orderType("orderType").build();
        Review review3 = Review.builder().reviewDate(LocalDateTime.of(2018 , 9 , 21 , 8 , 15 , 00)).score(5).content("리뷰 데이터 3").writer(accountDetail3).product(product1).orderType("orderType").build();
        Review review4 = Review.builder().reviewDate(LocalDateTime.of(2019 , 4 , 1 , 21 , 31 , 00)).score(1).content("리뷰 데이터 4").writer(accountDetail3).product(product2).orderType("orderType").build();
        Review review5 = Review.builder().reviewDate(LocalDateTime.of(2019 , 6 , 7 , 18 , 53 , 00)).score(3).content("리뷰 데이터 5").writer(accountDetail2).product(product2).orderType("orderType").build();
        product1.addReview(review1);
        product1.addReview(review2);
        product1.addReview(review3);
        product2.addReview(review4);
        product2.addReview(review5);

        // WishList
        Wish wish1 = Wish.builder().product(product1).user(accountDetail1).build();
        Wish wish2 = Wish.builder().product(product2).user(accountDetail1).build();
        Wish wish3 = Wish.builder().product(product6).user(accountDetail1).build();
        Wish wish4 = Wish.builder().product(product10).user(accountDetail1).build();
        Wish wish5 = Wish.builder().product(product1).user(accountDetail2).build();
        Wish wish6 = Wish.builder().product(product2).user(accountDetail2).build();
        Wish wish7 = Wish.builder().product(product6).user(accountDetail2).build();
        Wish wish8 = Wish.builder().product(product10).user(accountDetail2).build();

        //Cart
        Cart cart1 = Cart.builder().user(accountDetail1).product(product1).amount(1).build();
        Cart cart2 = Cart.builder().user(accountDetail1).product(product2).amount(2).build();
        Cart cart3 = Cart.builder().user(accountDetail2).product(product3).amount(3).build();

        //Coupon
        Coupon coupon1 = Coupon.builder().couponName("1번 쿠폰").saleRate(11).product(product1).build();
        Coupon coupon2 = Coupon.builder().couponName("2번 쿠폰").saleRate(22).product(product1).build();
        Coupon coupon3 = Coupon.builder().couponName("3번 쿠폰").saleRate(33).product(product2).build();

        //AccountDetail.ShippingAddress
        accountDetail1.setShippingAddress("충북 충주시 A아파트");

        productRepository.saveAll(Arrays.asList(product1, product2, product3, product4, product5, product6, product7, product8, product9, product10));
        wishRepository.saveAll(Arrays.asList(wish1, wish2, wish3, wish4, wish5, wish6, wish7, wish8));
        followRepository.saveAll(Arrays.asList(follow1, follow2, follow3, follow4));
        cartRepository.saveAll(Arrays.asList(cart1, cart2, cart3));
        couponRepository.saveAll(Arrays.asList(coupon1, coupon2, coupon3));

    }
}
package com.team.leaf.common.init;

import com.team.leaf.shopping.product.product.entity.Product;
import com.team.leaf.shopping.product.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {
        // 초기 데이터 저장
        Product product1 = Product.builder().title("딸기 케이크").description("10년 경력의 베이킹 전문가가 만든 딸기 케이크입니다.").price(18000).discountRate(2.2).image("https://ibb.co/k27RhH2").saleRate(10).views(0).registrationDate(LocalDateTime.of(2018, 3 , 6 , 12 , 30 , 00)).build();
        Product product2 = Product.builder().title("휴지").description("1576칸으로 구성된 두루마니 휴지입니다. 멸균이 잘 되어있는 아주 깨끗한 휴지").price(3000).discountRate(1.7).image("https://ibb.co/FDGnKLc").saleRate(5).views(0).registrationDate(LocalDateTime.of(2021, 5 , 1 , 11 , 26 , 00)).build();
        Product product3 = Product.builder().title("후라이팬").description("총알도 거뜬히 막는 후라이팬, 대충 강해보이는 후라이팬 그렇다구요").price(9000).discountRate(1.8).image("https://ibb.co/LJNsDs2").saleRate(20).views(0).registrationDate(LocalDateTime.of(2019, 8 , 1 , 5 , 0 , 00)).build();
        Product product4 = Product.builder().title("신라면").description("전 국민이 좋아하는 신 라면, 옛날보다 버섯을 덜 넣어주지만 그래도 맛있어요").price(800).discountRate(1.0).image("https://ibb.co/sqdvg78").saleRate(0).views(0).registrationDate(LocalDateTime.of(2018, 3 , 6 , 12 , 30 , 00)).build();
        Product product5 = Product.builder().title("면 봉").description("대충 깨끗하고 멸균처리한 면봉이라는 설명").price(9000).discountRate(4.5).image("https://ibb.co/Ptjpvbc").saleRate(10).views(0).registrationDate(LocalDateTime.of(2018, 3 , 6 , 12 , 30 , 00)).build();
        Product product6 = Product.builder().title("보조 배터리").description("500000maH 용량을 가진 보조 배터리, 참고로 자동차 배터리보다 용량이 크다. 아무도 인읽을 것 같으니 막 쓴다.").price(50000).discountRate(5.5).image("https://ibb.co/7vnG7ff").saleRate(30).views(0).registrationDate(LocalDateTime.of(2018, 3 , 6 , 12 , 30 , 00)).build();
        Product product7 = Product.builder().title("마스크").description("COVID-19를 막아주는 마스크입니다. 심지어 못생긴 하관도 막아주는 만능 마스크").price(2000).discountRate(1.9).image("https://ibb.co/0rXq2LK").saleRate(8).views(0).registrationDate(LocalDateTime.of(2018, 3 , 6 , 12 , 30 , 00)).build();
        Product product8 = Product.builder().title("손톱깎이").description("철도 자른다는 강력한 손톱깎이").price(5000).discountRate(5.2).image("https://ibb.co/VTLmWgH").saleRate(20).views(0).registrationDate(LocalDateTime.of(2024, 3 , 6 , 12 , 30 , 00)).build();
        Product product9 = Product.builder().title("흑채").description("흑채.,. 내가 죽을 때 가지 쓸일이 없길 바라는 마음").price(18000).discountRate(5.0).image("https://ibb.co/rFtJzHg").saleRate(20).views(0).registrationDate(LocalDateTime.of(2018, 3 , 6 , 12 , 30 , 00)).build();
        Product product10 = Product.builder().title("꽃병").description("꽃병이다. 꽃을 넣을 수 있는 병이다. 그렇다고").price(5000).discountRate(6.2).image("https://ibb.co/zRZj4bF").saleRate(10).views(0).registrationDate(LocalDateTime.of(2018, 3 , 6 , 12 , 30 , 00)).build();

        productRepository.saveAll(Arrays.asList(product1, product2 , product3 , product4 , product5 , product6 , product7 , product8 , product9 , product10));
    }
}
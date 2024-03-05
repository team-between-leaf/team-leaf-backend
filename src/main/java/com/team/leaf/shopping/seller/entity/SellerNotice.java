package com.team.leaf.shopping.seller.entity;

import com.team.leaf.shopping.seller.dto.SellerNoticeRequest;
import com.team.leaf.user.account.entity.AccountDetail;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellerNotice {

    @Id
    @GeneratedValue
    private long sellerNoticeId;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private AccountDetail writer;

    @CreationTimestamp
    private LocalDateTime writeDate;

    public static SellerNotice createSellerNotice(AccountDetail accountDetail, SellerNoticeRequest request) {
        return SellerNotice.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .writer(accountDetail)
                .build();
    }

}

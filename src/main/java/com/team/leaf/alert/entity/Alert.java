package com.team.leaf.alert.entity;

import com.team.leaf.user.account.entity.AccountDetail;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Alert {

    @Id @GeneratedValue
    public long alertId;

    public long sessionId;

    @OneToOne
    AccountDetail accountDetail;

    public static Alert createAlert(long sessionId, AccountDetail accountDetail) {
        return Alert.builder()
                .sessionId(sessionId)
                .accountDetail(accountDetail)
                .build();
    }

}

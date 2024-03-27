package com.team.leaf.shopping.follow.dto;

import com.team.leaf.user.account.entity.AccountDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowRes {

    private Long userId;

    private String nickname;

    private FollowRes createFollowRes(AccountDetail accountDetail) {
        FollowRes response = new FollowRes();
        response.setUserId(accountDetail.getUserId());
        response.setNickname(accountDetail.getNickname());

        return response;
    }

}

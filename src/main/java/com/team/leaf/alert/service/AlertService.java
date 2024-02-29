package com.team.leaf.alert.service;

import com.team.leaf.alert.dto.AlertRequest;
import com.team.leaf.user.account.entity.AccountDetail;
import com.team.leaf.user.account.entity.AccountPrivacy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.team.leaf.alert.dto.AlertType.*;

@Service
@RequiredArgsConstructor
public class AlertService {

    @Transactional
    public void updateAlertNotify(AccountDetail accountDetail, AlertRequest request) {
        AccountPrivacy accountPrivacy = accountDetail.getUserDetail();

        updateNotifyData(accountPrivacy, request);
    }

    private void updateNotifyData(AccountPrivacy account, AlertRequest request) {
        if(request.getAlertType().equals(ORDER_DELIVERY)) {
            account.updateOrderDeliveryNotify(request.isSetting());
        }
        else if(request.getAlertType().equals(FOLLOWED_SELLER)) {
            account.updateFollowedSellerNotify(request.isSetting());
        }
        else if(request.getAlertType().equals(MARKETING)) {
            account.updateMarketingNotify(request.isSetting());
        }
        else if(request.getAlertType().equals(WISHLIST)) {
            account.updateWishlistNotify(request.isSetting());
        }
    }
}

package com.team2final.minglecrm.controller.reward.response;

import com.team2final.minglecrm.entity.reward.Reward;
import com.team2final.minglecrm.entity.reward.Voucher;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RewardResponse {
    private final Long customerId;
    private final Long amount;

    public static RewardResponse of (Reward reward){
        return new RewardResponse(
                reward.getCustomer().getId(),
                reward.getAmount()
        );
    }
}

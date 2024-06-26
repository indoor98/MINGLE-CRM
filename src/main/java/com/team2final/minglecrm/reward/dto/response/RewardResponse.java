package com.team2final.minglecrm.reward.dto.response;

import com.team2final.minglecrm.reward.domain.Reward;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RewardResponse {
    private final Long customerId;
    private final String customerName;
    private final Long amount;

    public static RewardResponse of (Reward reward){
        return new RewardResponse(
                reward.getCustomer().getId(),
                reward.getCustomer().getName(),
                reward.getAmount()
        );
    }
}

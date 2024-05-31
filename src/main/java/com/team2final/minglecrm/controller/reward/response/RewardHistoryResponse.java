package com.team2final.minglecrm.controller.reward.response;

import com.team2final.minglecrm.entity.reward.RewardHistory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class RewardHistoryResponse {
    private final Long customerId;
    private final Long paymentId;
    private final Long voucherId;
    private final Long amount;
    private final String reason;
    private final LocalDateTime date;

    public static RewardHistoryResponse of(RewardHistory rewardHistory){
        return new RewardHistoryResponse(
                rewardHistory.getReward().getCustomer().getId(),
                rewardHistory.getPayment().getId(),
                rewardHistory.getVoucher().getId(),
                rewardHistory.getAmount(),
                rewardHistory.getReason(),
                rewardHistory.getDate()
        );
    }

}

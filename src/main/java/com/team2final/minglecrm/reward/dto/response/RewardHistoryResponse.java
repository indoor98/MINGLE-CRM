package com.team2final.minglecrm.reward.dto.response;

import com.team2final.minglecrm.reward.domain.RewardHistory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class RewardHistoryResponse {
    private final Long rewardHistoryId;
    private final Long customerId;
    private final String customerName;
    private final Long paymentId;
    private final Long voucherId;
    private final Long amount;
    private final String reason;
    private final LocalDateTime date;
    private final String type;

    public static RewardHistoryResponse of(RewardHistory rewardHistory){
        Long voucherId = rewardHistory.getVoucher() != null ? rewardHistory.getVoucher().getId() : null;
        Long paymentId = rewardHistory.getPayment() != null ? rewardHistory.getPayment().getId() : null;

        return new RewardHistoryResponse(
                rewardHistory.getId(),
                rewardHistory.getReward().getCustomer().getId(),
                rewardHistory.getReward().getCustomer().getName(),
//                rewardHistory.getPayment().getId(),
                paymentId,
//                rewardHistory.getVoucher().getId(),
                voucherId,
                rewardHistory.getAmount(),
                rewardHistory.getReason(),
                rewardHistory.getDate(),
                rewardHistory.getType()
        );
    }

}

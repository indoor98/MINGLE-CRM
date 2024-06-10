package com.team2final.minglecrm.controller.reward.response;

import com.team2final.minglecrm.entity.reward.VoucherHistory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class VoucherApprovalResponse {
    private final Long voucherId;
    private final Boolean isAuth;
    private final LocalDateTime authDate;

    public static VoucherApprovalResponse of(VoucherHistory voucherHistory){
        return new VoucherApprovalResponse(
                voucherHistory.getVoucher().getId(),
                voucherHistory.getIsAuth(),
                voucherHistory.getAuthDate()
        );
    }
}

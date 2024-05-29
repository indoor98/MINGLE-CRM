package com.team2final.minglecrm.controller.reward.response;

import com.team2final.minglecrm.entity.reward.VoucherHistory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VoucherStatusResponse {
    private final Long voucherId;
    private final Boolean isAuth;

    public static VoucherStatusResponse of(VoucherHistory voucherHistory){
        return new VoucherStatusResponse(
                voucherHistory.getVoucher().getId(),
                voucherHistory.getIsAuth()
        );
    }
}

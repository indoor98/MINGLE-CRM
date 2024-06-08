package com.team2final.minglecrm.controller.reward.response;

import com.team2final.minglecrm.entity.reward.Voucher;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VoucherStatusResponse {
    private final Long voucherId;
    private final String voucherStatus;

    public static VoucherStatusResponse of(Voucher voucher, String status) {
        return new VoucherStatusResponse(voucher.getId(), status);
    }
}

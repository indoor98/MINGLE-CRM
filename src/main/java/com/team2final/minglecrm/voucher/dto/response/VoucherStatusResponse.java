package com.team2final.minglecrm.voucher.dto.response;

import com.team2final.minglecrm.voucher.domain.Voucher;
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

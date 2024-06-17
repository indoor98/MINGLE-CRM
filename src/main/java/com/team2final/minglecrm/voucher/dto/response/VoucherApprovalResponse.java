package com.team2final.minglecrm.voucher.dto.response;

import com.team2final.minglecrm.voucher.domain.VoucherHistory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class VoucherApprovalResponse {
    private final Long voucherId;
    private final LocalDateTime authDate;

    public static VoucherApprovalResponse of(VoucherHistory voucherHistory){
        return new VoucherApprovalResponse(
                voucherHistory.getVoucher().getId(),
                voucherHistory.getConfirmDate()
        );
    }
}

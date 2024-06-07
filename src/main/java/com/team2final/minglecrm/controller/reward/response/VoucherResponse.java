package com.team2final.minglecrm.controller.reward.response;

import com.team2final.minglecrm.entity.reward.Voucher;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class VoucherResponse {
    private final Long customerId;
    private final String customerName;
    private final Long employeeId;
    private final String employeeName;
    private final Long amount;
    private final LocalDateTime createDate;
    private final LocalDateTime expireDate;
    private final String voucherCode;


    public static VoucherResponse of(Voucher voucher) {
        return new VoucherResponse(
                voucher.getCustomer().getId(),
                voucher.getCustomer().getName(),
                voucher.getEmployee().getId(),
                voucher.getEmployee().getName(),
                voucher.getAmount(),
                voucher.getCreateDate(),
                voucher.getExpiredDate(),
                voucher.getVoucherCode());
    }
}

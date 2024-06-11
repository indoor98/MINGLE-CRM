package com.team2final.minglecrm.controller.reward.response;

import com.team2final.minglecrm.entity.reward.VoucherHistory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;


@Getter
@RequiredArgsConstructor
public class VoucherHistoryResponse {
    private final Long voucherId;
    private final LocalDateTime requestDate;
    private final Boolean isAuth;
    private final LocalDateTime authDate;
    private final Boolean isConvertedYn;
    private final LocalDateTime conversionDate;
    private final Long issuerId;
    private final Long approverId;
    private final Long customerId;
    private final Long amount;
    private final String voucherCode;


    public static VoucherHistoryResponse of(VoucherHistory voucherHistory){
        Long employeeManagerId = voucherHistory.getEmployeeManager() != null ? voucherHistory.getEmployeeManager().getId() : null;

        return new VoucherHistoryResponse(
                voucherHistory.getVoucher().getId(),
                voucherHistory.getRequestDate(),
                voucherHistory.getIsAuth(),
                voucherHistory.getAuthDate(),
                voucherHistory.getIsConverted(),
                voucherHistory.getConversionDate(),
                voucherHistory.getEmployeeStaff().getId(),
                employeeManagerId,
                voucherHistory.getCustomer().getId(),
                voucherHistory.getVoucher().getAmount(),
                voucherHistory.getVoucher().getVoucherCode()
        );
    }
}

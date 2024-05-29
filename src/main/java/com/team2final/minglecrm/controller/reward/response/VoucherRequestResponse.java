package com.team2final.minglecrm.controller.reward.response;

import com.team2final.minglecrm.entity.reward.Voucher;
import com.team2final.minglecrm.entity.reward.VoucherHistory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class VoucherRequestResponse {
    private Long customerId;
    private Long employeeId;
    private LocalDateTime requestDate;
    private String voucherCode;

    @Builder
    public VoucherRequestResponse(Long customerId, Long employeeId, LocalDateTime requestDate, String voucherCode) {
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.requestDate = requestDate;
        this.voucherCode = voucherCode;
    }

    public static VoucherRequestResponse of(VoucherHistory voucherHistory) {
        return new VoucherRequestResponse(
                voucherHistory.getCustomer().getId(),
                voucherHistory.getEmployeeStaff().getId(),
                voucherHistory.getRequestDate(),
                voucherHistory.getVoucher().getVoucherCode()
        );
    }

}
package com.team2final.minglecrm.controller.reward.response;

import com.team2final.minglecrm.entity.reward.Voucher;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class VoucherCreateResponse {
    private Long customerId;
    private Long EmployeeId;
    private Long amount;
    private LocalDateTime createDate;
    private LocalDateTime expireDate;
    private String voucherCode;

    @Builder
    public VoucherCreateResponse(Long customerId, String customerName, Long employeeId, Long amount, LocalDateTime createDate, LocalDateTime expireDate, String voucherCode) {
        this.customerId = customerId;
        this.EmployeeId = employeeId;
        this.amount = amount;
        this.createDate = createDate;
        this.expireDate = expireDate;
        this.voucherCode = voucherCode;
    }
}

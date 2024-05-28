package com.team2final.minglecrm.controller.reward.response;

import com.team2final.minglecrm.entity.reward.Voucher;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class VoucherCreateResponse {
    private Long customerId;
    private String customerName;
    private Long employeeId;
    private String employeeName;
    private Long amount;
    private LocalDateTime createDate;
    private LocalDateTime expireDate;
    private String voucherCode;

    @Builder
    public VoucherCreateResponse(Long customerId, String customerName, Long employeeId, String employeeName, Long amount, LocalDateTime createDate, LocalDateTime expireDate, String voucherCode) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.amount = amount;
        this.createDate = createDate;
        this.expireDate = expireDate;
        this.voucherCode = voucherCode;
    }
}

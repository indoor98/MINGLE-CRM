package com.team2final.minglecrm.voucher.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import com.team2final.minglecrm.voucher.domain.Voucher;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class VoucherResponse {
    private final Long voucherId;
    private final Long customerId;
    private final String customerName;
    private final Long employeeId;
    private final String employeeName;
    private final Long amount;
    private final LocalDateTime createdDate;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final String createdReason;


    public static VoucherResponse of(Voucher voucher) {
        Long employeeId = voucher.getEmployee() != null ? voucher.getEmployee().getId() : null;
        String employeeName = voucher.getEmployee() != null ? voucher.getEmployee().getName() : null;

        return new VoucherResponse(
                voucher.getId(),
                voucher.getCustomer().getId(),
                voucher.getCustomer().getName(),
                employeeId,
                employeeName,
                voucher.getAmount(),
                voucher.getCreatedDate(),
                voucher.getStartDate(),
                voucher.getEndDate(),
                voucher.getCreatedReason());
    }

    @QueryProjection
    public VoucherResponse(Long voucherId, Long customerId, String customerName, Long employeeId, String employeeName, Long amount, LocalDateTime createdDate, LocalDateTime startDate, LocalDateTime endDate, String createdReason) {
        this.voucherId = voucherId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.amount = amount;
        this.createdDate = createdDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdReason = createdReason;
    }
}
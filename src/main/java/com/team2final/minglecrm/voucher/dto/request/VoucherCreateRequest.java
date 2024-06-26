package com.team2final.minglecrm.voucher.dto.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class VoucherCreateRequest {
    private Long customerId;
    private Long amount;
    private String reason;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
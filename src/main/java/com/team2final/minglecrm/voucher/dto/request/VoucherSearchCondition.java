package com.team2final.minglecrm.voucher.dto.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class VoucherSearchCondition {

    private LocalDateTime requestDate;

    private String createdReason;

    private LocalDateTime confirmDate;

    private LocalDateTime conversionDate;

    private String creatorName;

    private Long confirmerId;

    private String confirmerName;

    private String customerName;

    private String customerEmail;

    private Long amount;

    private String rejectedReason;

    private String voucherCode;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String customerGrade;

}
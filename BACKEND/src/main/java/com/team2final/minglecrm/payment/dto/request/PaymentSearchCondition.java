package com.team2final.minglecrm.payment.dto.request;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PaymentSearchCondition {

    private String customerName;
    private String number;
    private String type;
    private Long paymentAmount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
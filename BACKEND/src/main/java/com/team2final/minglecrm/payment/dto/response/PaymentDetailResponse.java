package com.team2final.minglecrm.payment.dto.response;

import com.team2final.minglecrm.payment.domain.Payment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class PaymentDetailResponse {

    // 고객명, 전화번호
    private final String customerName;
    private final String number;

    // 결제 유형, 할인 전 금액, 할인 금액, 결제 금액, 결제 날짜
    private final String type;
    private final Long amountBeforeDiscount;
    private final Long discountAmount;
    private final Long paymentAmount;
    private final LocalDateTime paymentDate;

    // 환불 여부, 환불 날짜
    private final Boolean isRefunded;
    private final LocalDateTime refundDate;

    // 포인트, 결제 지점
    private final Long createdReward;
    private final String paymentSpot;


    public static PaymentDetailResponse from(Payment payment) {
        return new PaymentDetailResponse(
                payment.getCustomer().getName(),
                payment.getCustomer().getPhone(),
                payment.getType(),
                payment.getAmountBeforeDiscount(),
                payment.getDiscountAmount(),
                payment.getPaymentAmount(),
                payment.getPaymentDate(),
                payment.getIsRefunded(),
                payment.getRefundDate(),
                payment.getCreatedReward(),
                payment.getPaymentSpot()
        );
    }
}

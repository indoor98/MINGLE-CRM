package com.team2final.minglecrm.payment.dto.response;

import com.team2final.minglecrm.payment.domain.Payment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class PaymentResponse {

    private final Long id;
    // 고객명, 전화번호
    private final String customerName;
    private final String number;

    // 결제 유형, 할인 금액, 결제 금액, 결제 날짜
    private final String type;
    private final Long paymentAmount;
    private final LocalDateTime paymentDate;

    // 환불 여부
    private final Boolean isRefunded;

    public static PaymentResponse from(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getCustomer().getName(),
                payment.getCustomer().getPhone(),
                payment.getType(),
                payment.getPaymentAmount(),
                payment.getPaymentDate(),
                payment.getIsRefunded()
        );
    }
}

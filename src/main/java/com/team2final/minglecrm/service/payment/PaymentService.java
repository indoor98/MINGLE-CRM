package com.team2final.minglecrm.service.payment;

import com.team2final.minglecrm.controller.payment.response.PaymentResponse;
import com.team2final.minglecrm.entity.payment.Payment;
import com.team2final.minglecrm.persistence.repository.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public List<PaymentResponse> findById(Long customerId) {
        List<Payment> payments = paymentRepository.findByCustomerId(customerId);

        return payments.stream()
                .map(PaymentResponse::of)
                .collect(Collectors.toList());
    }

    public PaymentResponse findPaymentById(Long customerId, Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow();
        return PaymentResponse.of(payment);
    }
}

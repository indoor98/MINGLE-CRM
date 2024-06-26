package com.team2final.minglecrm.payment.domain.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team2final.minglecrm.payment.domain.QPayment;
import com.team2final.minglecrm.payment.dto.request.PaymentSearchCondition;
import com.team2final.minglecrm.payment.dto.response.PaymentResponse;
import com.team2final.minglecrm.payment.dto.response.QPaymentResponse;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PaymentSearchRepository {

    private final JPAQueryFactory queryFactory;

    public PaymentSearchRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<PaymentResponse> search(Long customerId, PaymentSearchCondition condition) {
        QPayment payment = QPayment.payment;

        return queryFactory
                .select(new QPaymentResponse(
                        payment.id,
                        payment.customer.name,
                        payment.customer.phone,
                        payment.type,
                        payment.paymentAmount,
                        payment.paymentDate,
                        payment.isRefunded
                ))
                .from(payment)
                .where(
                        customerIdEq(customerId),
                        customerNameEq(condition.getCustomerName()),
                        numberEq(condition.getNumber()),
                        typeEq(condition.getType()),
                        paymentAmountEq(condition.getPaymentAmount()),
                        paymentDateBetween(condition.getStartDate(), condition.getEndDate())
                )
                .fetch();
    }

    private BooleanExpression customerIdEq(Long customerId) {
        return customerId != null ? QPayment.payment.customer.id.eq(customerId) : null;
    }

    private BooleanExpression customerNameEq(String customerName) {
        return customerName != null ? QPayment.payment.customer.name.eq(customerName) : null;
    }

    private BooleanExpression numberEq(String number) {
        return number != null ? QPayment.payment.customer.phone.eq(number) : null;
    }

    private BooleanExpression typeEq(String type) {
        return type != null ? QPayment.payment.type.eq(type) : null;
    }

    private BooleanExpression paymentAmountEq(Long paymentAmount) {
        return paymentAmount != null ? QPayment.payment.paymentAmount.eq(paymentAmount) : null;
    }

    private BooleanExpression paymentDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate != null && endDate != null) {
            return QPayment.payment.paymentDate.between(startDate, endDate);
        } else if (startDate != null) {
            return QPayment.payment.paymentDate.goe(startDate);
        } else if (endDate != null) {
            return QPayment.payment.paymentDate.loe(endDate);
        } else {
            return null;
        }
    }
}
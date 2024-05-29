package com.team2final.minglecrm.persistence.repository.payment;

import com.team2final.minglecrm.entity.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByCustomerId(Long customerId);
}

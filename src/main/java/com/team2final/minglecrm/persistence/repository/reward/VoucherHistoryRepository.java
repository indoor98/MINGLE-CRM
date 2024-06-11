package com.team2final.minglecrm.persistence.repository.reward;

import com.team2final.minglecrm.entity.customer.Customer;
import com.team2final.minglecrm.entity.reward.VoucherHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoucherHistoryRepository extends JpaRepository<VoucherHistory, Long> {
    Optional<VoucherHistory> findByVoucherId(Long voucherId);
    List<VoucherHistory> findAllByCustomer(Customer customer);
    VoucherHistory findByCustomerAndVoucherId(Customer customer, Long voucherId);
    List<VoucherHistory> findByIsAuthFalse();
}
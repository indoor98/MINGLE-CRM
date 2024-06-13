package com.team2final.minglecrm.persistence.repository.reward;

import com.team2final.minglecrm.entity.customer.Customer;
import com.team2final.minglecrm.entity.reward.VoucherHistory;
import com.team2final.minglecrm.entity.reward.status.VoucherStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherHistoryRepository extends JpaRepository<VoucherHistory, Long> {
    VoucherHistory findByVoucherId(Long voucherId);
    List<VoucherHistory> findAllByCustomer(Customer customer);
    VoucherHistory findByCustomerAndVoucherId(Customer customer, Long voucherId);
//    List<VoucherHistory> findByIsAuthFalse();
    List<VoucherHistory> findAllByStatus(VoucherStatusType status);

}
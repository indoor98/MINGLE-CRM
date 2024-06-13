package com.team2final.minglecrm.persistence.repository.reward;

import com.team2final.minglecrm.entity.customer.Customer;
import com.team2final.minglecrm.entity.employee.Employee;
import com.team2final.minglecrm.entity.reward.Voucher;
import com.team2final.minglecrm.entity.reward.VoucherHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
//    boolean existsByVoucherCode(String voucherCode);
//
//    @Query("SELECT v, vh.status FROM Voucher v LEFT JOIN VoucherHistory vh ON v.id = vh.voucher.id WHERE v.employee.id = :employeeId")
//    List<Object[]> findAllVouchersWithAuthStatus(@Param("employeeId") Long employeeId);
    List<Voucher> findAllByIsRequested(boolean isRequest);

}

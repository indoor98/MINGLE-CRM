package com.team2final.minglecrm.voucher.domain.repository;

import com.team2final.minglecrm.employee.domain.Employee;
import com.team2final.minglecrm.voucher.domain.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {

    List<Voucher> findAllByIsRequestedAndEmployee(boolean isRequest, Employee employee);

}
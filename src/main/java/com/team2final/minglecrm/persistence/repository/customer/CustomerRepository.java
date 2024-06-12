package com.team2final.minglecrm.persistence.repository.customer;

import com.team2final.minglecrm.entity.customer.Customer;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);

    List<Customer> findByEmployeeName(String employeeName);

    List<Customer> findByGrade(String grade);

    Page<Customer> findAllBy(Pageable pageable);

    List<Customer> findByCreatedDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    @Query("SELECT c FROM Customer c JOIN FETCH c.roomReservations r WHERE r.startDate <= :endDate AND r.endDate >= :startDate")
    Page<Customer> findCustomersByReservationDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);
}

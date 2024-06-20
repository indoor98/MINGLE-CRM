package com.team2final.minglecrm.statistics.domain.repository;

import com.team2final.minglecrm.statistics.domain.FrequentCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FrequentCustomerRepository extends JpaRepository<FrequentCustomer, Long> {

    long countByGender(String gender);
    long countByGrade(String graded);
}

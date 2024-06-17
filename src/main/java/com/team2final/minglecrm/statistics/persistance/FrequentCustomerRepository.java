package com.team2final.minglecrm.statistics.persistance;

import com.team2final.minglecrm.statistics.entity.FrequentCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FrequentCustomerRepository extends JpaRepository<FrequentCustomer, Long> {

    long countByGender(String gender);
    long countByGrade(String graded);
}

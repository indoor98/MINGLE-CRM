package com.team2final.minglecrm.customer.domain.repository;

import com.team2final.minglecrm.customer.domain.CustomerPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerPreferenceRepository extends JpaRepository<CustomerPreference, Long> {

    CustomerPreference findByCustomerId(Long customerId);
}

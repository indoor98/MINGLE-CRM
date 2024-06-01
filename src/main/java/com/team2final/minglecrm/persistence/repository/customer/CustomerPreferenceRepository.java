package com.team2final.minglecrm.persistence.repository.customer;

import com.team2final.minglecrm.entity.customer.CustomerPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerPreferenceRepository extends JpaRepository<CustomerPreference, Long> {

    CustomerPreference findByCustomerId(Long customerId);
}

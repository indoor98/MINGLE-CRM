package com.team2final.minglecrm.registration.domain.repository;

import com.team2final.minglecrm.registration.domain.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    Registration findByEmail(String email);


}

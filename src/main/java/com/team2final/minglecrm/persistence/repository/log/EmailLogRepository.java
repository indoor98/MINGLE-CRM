package com.team2final.minglecrm.persistence.repository.log;

import com.team2final.minglecrm.entity.customer.Customer;
import com.team2final.minglecrm.entity.event.Event;
import com.team2final.minglecrm.entity.log.EmailLog;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailLogRepository extends JpaRepository<EmailLog, Long> {

    EmailLog findByEventAndCustomer(Event event, Customer customer);
    Long countByEventId(Long eventId);
}

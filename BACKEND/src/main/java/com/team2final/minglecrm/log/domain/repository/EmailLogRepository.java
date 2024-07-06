package com.team2final.minglecrm.log.domain.repository;

import com.team2final.minglecrm.customer.domain.Customer;
import com.team2final.minglecrm.event.domain.Event;
import com.team2final.minglecrm.log.domain.EmailLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailLogRepository extends JpaRepository<EmailLog, Long> {

    EmailLog findByEventAndCustomer(Event event, Customer customer);
    Long countByEventId(Long eventId);
}

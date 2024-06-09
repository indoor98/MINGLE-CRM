package com.team2final.minglecrm.service.log;

import com.team2final.minglecrm.entity.customer.Customer;
import com.team2final.minglecrm.entity.event.Event;
import com.team2final.minglecrm.entity.log.EmailLog;
import com.team2final.minglecrm.persistence.repository.customer.CustomerRepository;
import com.team2final.minglecrm.persistence.repository.event.EventRepository;
import com.team2final.minglecrm.persistence.repository.log.EmailLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogService {

    private final EmailLogRepository emailLogRepository;
    private final CustomerRepository customerRepository;
    private final EventRepository eventRepository;

    public void createEmailLog(Long eventId, String customerEmail) throws Exception {

        Event event =
                eventRepository.findById(eventId).orElseThrow(Exception::new);

        Customer customer =
                customerRepository.findByEmail(customerEmail).orElseThrow(Exception::new);

        EmailLog emailLog = EmailLog.builder()
                .customer(customer)
                .event(event)
                .build();

        emailLogRepository.save(emailLog);
    }

}

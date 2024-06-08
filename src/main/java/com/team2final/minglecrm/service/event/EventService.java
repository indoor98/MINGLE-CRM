package com.team2final.minglecrm.service.event;

import com.team2final.minglecrm.controller.event.request.EventEmailSendRequest;
import com.team2final.minglecrm.controller.event.request.ToEmailRequest;
import com.team2final.minglecrm.entity.customer.Customer;
import com.team2final.minglecrm.entity.employee.Employee;
import com.team2final.minglecrm.entity.event.Event;
import com.team2final.minglecrm.entity.log.EmailLog;
import com.team2final.minglecrm.persistence.repository.customer.CustomerRepository;
import com.team2final.minglecrm.persistence.repository.employee.EmployeeRepository;
import com.team2final.minglecrm.persistence.repository.event.EventRepository;
import com.team2final.minglecrm.persistence.repository.log.EmailLogRepository;
import com.team2final.minglecrm.service.email.EmailSendService;
import com.team2final.minglecrm.service.log.LogService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EmailSendService emailSendService;
    private final EventRepository eventRepository;
    private final EmployeeRepository employeeRepository;
    private final EmailLogRepository emailLogRepository;
    private final CustomerRepository customerRepository;
    private final LogService logService;

    public void sendEventEmail(EventEmailSendRequest request) throws Exception {

        // 요청한 이메일 주소를 통해 직원 정보를 조회합니다.
        Employee employee = employeeRepository.findByEmail(request.getFromEmail())
                .orElseThrow(() -> new Exception("Employee not found"));

        // 이벤트 객체를 생성합니다.
        Event event = Event.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .employee(employee)
                .sentDate(LocalDateTime.now())
                .build();

        // 이벤트 객체를 데이터베이스에 저장합니다.
        Event savedEvent = eventRepository.save(event);

        // 저장된 이벤트 객체의 ID가 null이 아닌지 확인합니다.
        if (savedEvent.getId() == null) {
            throw new Exception("Event ID is null after saving");
        }

        // 이벤트가 성공적으로 저장된 후, 각 수신자에게 이메일을 보냅니다.
        for(ToEmailRequest toEmailRequest : request.getToEmail()) {
            String toEmail = toEmailRequest.getEmail();
            emailSendService.sendMail(toEmail, request.getTitle(), request.getContent());

            // 이메일 로그를 생성합니다.
            logService.createEmailLog(savedEvent.getId(), toEmail);
        }
    }

    public void emailOpenCheck(Long eventId, String customerEmail) throws Exception {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(Exception::new);

        Customer customer = customerRepository
                .findByEmail(customerEmail).orElseThrow(Exception::new);

        EmailLog emailLog = emailLogRepository.findByEventAndCustomer(event, customer);
        emailLog.readCheck();
    }
}

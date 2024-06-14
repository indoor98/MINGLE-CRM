package com.team2final.minglecrm.service.email;

import com.team2final.minglecrm.controller.event.request.CreateEventRequest;
import com.team2final.minglecrm.controller.event.request.PersonalEmailSendRequest;
import com.team2final.minglecrm.entity.customer.Customer;
import com.team2final.minglecrm.entity.event.Event;
import com.team2final.minglecrm.entity.log.EmailLog;
import com.team2final.minglecrm.persistence.repository.customer.CustomerRepository;
import com.team2final.minglecrm.persistence.repository.employee.EmployeeRepository;
import com.team2final.minglecrm.persistence.repository.event.EventRepository;
import com.team2final.minglecrm.persistence.repository.log.EmailLogRepository;
import com.team2final.minglecrm.service.event.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailSendService emailSendService;
    private final EmployeeRepository employeeRepository;
    private final EventService eventService;
    private final EmailLogRepository emailLogRepository;
    private final CustomerRepository customerRepository;
    private final EventRepository eventRepository;


    public void sendPersonalEmail(PersonalEmailSendRequest request, String employeeEmail) throws Exception {

        // 이벤트 생성
        CreateEventRequest createEventRequest = CreateEventRequest.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .employeeEmail(employeeEmail)
                .sendCount(1L)
                .build();

        Long eventId = eventService.createEvent(createEventRequest);

        Event event = eventRepository.findById(eventId).orElseThrow( () -> new Exception("없는 이벤트입니다."));
        Customer customer = customerRepository.findByEmail(request.getToEmail()).orElseThrow( () ->
                new IllegalArgumentException("없는 고객입니다."));

        // 이메일 보내기
        String content = request.getContent();
        content = "<img src=http://localhost:8080/readcheck/" + eventId.toString() + "/" + request.getToEmail() + "\"" + "/>";
        emailSendService.sendMail(request.getToEmail(), request.getTitle(), content);

        // 이메일 로그 생성


        EmailLog emailLog = EmailLog.builder()
                .event(event)
                .customer(customer)
                .build();

        emailLogRepository.save(emailLog);
    }
}

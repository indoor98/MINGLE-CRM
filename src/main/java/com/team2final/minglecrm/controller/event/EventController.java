package com.team2final.minglecrm.controller.event;


import com.team2final.minglecrm.controller.ResultResponse;
import com.team2final.minglecrm.controller.employee.vo.Subject;
import com.team2final.minglecrm.controller.event.request.EventEmailSendRequest;
import com.team2final.minglecrm.controller.event.request.PersonalEmailSendRequest;
import com.team2final.minglecrm.entity.employee.Employee;
import com.team2final.minglecrm.service.email.EmailSendService;
import com.team2final.minglecrm.service.email.EmailService;
import com.team2final.minglecrm.service.event.EventService;

import com.team2final.minglecrm.service.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final EmailSendService emailSendService;
    private final EmailService emailService;
    private final JwtProvider jwtProvider;

    @PostMapping("/api/event/email")
    public ResultResponse<Void> sendEventEmail(@RequestBody EventEmailSendRequest request) throws Exception {
        eventService.sendEventEmail(request);
        return new ResultResponse<>(HttpStatus.OK.value(), "success", null);
    }

    @GetMapping("/api/readcheck/{eventId}/{customerEmail}")
    public ResultResponse<Void> emailOpenLogging(
            @PathVariable(name = "eventId") Long eventId,
            @PathVariable(name = "customerEmail") String customerEmail ) throws Exception {

            eventService.emailOpenCheck(eventId, customerEmail);

        return new ResultResponse<>();
    }

    @PostMapping("/api/email/personal")
    public ResultResponse<Void> sendPersonalEmail(HttpServletRequest httpRequest, @RequestBody PersonalEmailSendRequest request) throws Exception {
        Employee employee = jwtProvider.getEmployeeFromHttpServletRequest(httpRequest);

        emailService.sendPersonalEmail(request, employee.getEmail());

        return new ResultResponse<>(HttpStatus.OK.value(), "success", null);
    }


}

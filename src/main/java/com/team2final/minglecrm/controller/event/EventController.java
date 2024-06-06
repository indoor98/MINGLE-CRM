package com.team2final.minglecrm.controller.event;


import com.team2final.minglecrm.controller.ResultResponse;
import com.team2final.minglecrm.controller.event.request.EventEmailSendRequest;
import com.team2final.minglecrm.service.event.EventService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    @PostMapping("/api/event/email")
    public ResultResponse<Void> sendEventEmail(@RequestBody EventEmailSendRequest request) throws Exception {
        eventService.sendEventEmail(request);
        return new ResultResponse<>(HttpStatus.OK.value(), "success", null);
    }

    @GetMapping("/api/event/read/{eventId}/{customerEmail}")
    public ResultResponse<Void> emailOpenLogging(
            @PathVariable(name = "eventId") Long eventId,
            @PathVariable(name = "customerEmail") String customerEmail ) throws Exception {

            eventService.emailOpenCheck(eventId, customerEmail);

        return new ResultResponse<>();
    }
}

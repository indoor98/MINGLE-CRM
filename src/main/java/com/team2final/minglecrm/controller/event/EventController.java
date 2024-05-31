package com.team2final.minglecrm.controller.event;

import com.team2final.minglecrm.service.email.EmailSendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final EmailSendService emailSendService;



}

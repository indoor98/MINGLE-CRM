package com.team2final.minglecrm.controller.event.request;

import lombok.Getter;

import java.util.List;

@Getter
public class EventEmailSendRequest {

    private String fromEmail;
    private String title;
    private String content;
    private List<ToEmailRequest> toEmail;
    private String eventUrl;

}

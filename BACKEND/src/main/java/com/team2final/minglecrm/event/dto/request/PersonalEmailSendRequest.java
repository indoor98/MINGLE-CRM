package com.team2final.minglecrm.event.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PersonalEmailSendRequest {

    private String toEmail;
    private String title;
    private String content;

    @Builder
    public PersonalEmailSendRequest(String toEmail, String title, String content) {
        this.toEmail = toEmail;
        this.title = title;
        this.content = content;

    }
}

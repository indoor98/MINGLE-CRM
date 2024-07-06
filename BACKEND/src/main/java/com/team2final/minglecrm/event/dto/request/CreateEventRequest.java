package com.team2final.minglecrm.event.dto.request;


import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateEventRequest {

    private String title;
    private String content;
    private String employeeEmail;
    private Long sendCount;

    @Builder
    public CreateEventRequest(String title, String content, String employeeEmail, Long sendCount) {
        this.title = title;
        this.content = content;
        this.employeeEmail = employeeEmail;
        this.sendCount = sendCount;
    }
}

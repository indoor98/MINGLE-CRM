package com.team2final.minglecrm.controller.event.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EventLogResponse {

    private Long eventId;
    private Long employeeId;
    private String employeeName;
    private String emailTitle;
    private String emailContent;
    private LocalDateTime sentDate;
    private Long sendCount;
    private Long readCount;

    @QueryProjection
    public EventLogResponse (Long eventId, Long employeeId, String employeeName, String emailTitle, String emailContent,
                             LocalDateTime sentDate, Long sendCount, Long readCount) {
        this.eventId = eventId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.emailTitle = emailTitle;
        this.emailContent = emailContent;
        this.sentDate = sentDate;
        this.sendCount = sendCount;
        this.readCount = readCount;

    }

}

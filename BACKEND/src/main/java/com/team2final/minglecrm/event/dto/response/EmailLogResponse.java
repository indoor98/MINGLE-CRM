package com.team2final.minglecrm.event.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EmailLogResponse {

    private String emailTitle;
    private String emailContent;
    private String employeeName;
    private Long employeeId;
    private LocalDateTime sentDate;
    private Long sendCount;
    private Long openCount;
    private Boolean isOpened;
    private LocalDateTime openedDate;
    private Long customerId;
    private String customerName;

    @QueryProjection
    public EmailLogResponse(
            String emailTitle,
            String emailContent,
            String employeeName,
            Long employeeId,
            LocalDateTime sentDate,
            Long sendCount,
            Long openCount,
            Boolean isOpened,
            LocalDateTime openedDate,
            Long customerId,
            String customerName
    ) {
        this.emailTitle = emailTitle;
        this.emailContent = emailContent;
        this.employeeName = employeeName;
        this.employeeId = employeeId;
        this.sentDate = sentDate;
        this.sendCount = sendCount;
        this.openCount = openCount;
        this.isOpened = isOpened;
        this.openedDate = openedDate;
        this.customerId = customerId;
        this.customerName = customerName;
    }


}

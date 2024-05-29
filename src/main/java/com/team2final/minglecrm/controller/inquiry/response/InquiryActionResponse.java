package com.team2final.minglecrm.controller.inquiry.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class InquiryActionResponse {
    private Long id;
    private Long inquiryId;
    private Boolean isActionNeeded;
    private String actionContent;
    private String email;
    private LocalDateTime date;

    @Builder
    public InquiryActionResponse(Long id, Long inquiryId, Boolean isActionNeeded, String actionContent, String email, LocalDateTime date){
        this.id = id;
        this.inquiryId = inquiryId;
        this.isActionNeeded = isActionNeeded;
        this.actionContent = actionContent;
        this.email = email;
        this.date = date;
    }
}

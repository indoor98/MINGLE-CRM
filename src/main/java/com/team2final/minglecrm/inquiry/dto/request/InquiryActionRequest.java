package com.team2final.minglecrm.inquiry.dto.request;

import lombok.Getter;

@Getter
public class InquiryActionRequest {
    private Long inquiryId;
    private String actionStatus;
    private String actionContent;
}

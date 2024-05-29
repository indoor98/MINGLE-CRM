package com.team2final.minglecrm.controller.inquiry.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class InquiryDetailResponse {
    private InquiryResponse inquiryResponse;
    private InquiryReplyResponse inquiryReplyResponse;
    private InquiryActionResponse inquiryActionResponse;

    @Builder
    public InquiryDetailResponse(InquiryResponse inquiryResponse, InquiryReplyResponse inquiryReplyResponse, InquiryActionResponse inquiryActionResponse) {
        this.inquiryResponse = inquiryResponse;
        this.inquiryReplyResponse = inquiryReplyResponse;
        this.inquiryActionResponse = inquiryActionResponse;
    }

}

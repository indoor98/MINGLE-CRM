package com.team2final.minglecrm.inquiry.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class InquiryReplyResponse {
    private Long id;
    private Long inquiryId;
    private String email;
    private String reply;
    private LocalDateTime date;

    @Builder
    public InquiryReplyResponse(Long id, Long inquiryId, String email, String reply, LocalDateTime date) {
        this.id = id;
        this.inquiryId = inquiryId;
        this.email = email;
        this.reply = reply;
        this.date = date;
    }
}

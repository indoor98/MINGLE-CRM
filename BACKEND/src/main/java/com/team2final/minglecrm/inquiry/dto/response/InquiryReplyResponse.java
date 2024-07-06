package com.team2final.minglecrm.inquiry.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class InquiryReplyResponse {
    private Long id;
    private Long inquiryId;
    private String email;
    private String name;
    private String reply;
    private LocalDateTime date;

    @Builder
    public InquiryReplyResponse(Long id, Long inquiryId, String email, String name, String reply, LocalDateTime date) {
        this.id = id;
        this.inquiryId = inquiryId;
        this.email = email;
        this.name = name;
        this.reply = reply;
        this.date = date;
    }
}

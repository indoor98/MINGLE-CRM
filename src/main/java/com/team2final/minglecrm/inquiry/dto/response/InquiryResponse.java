package com.team2final.minglecrm.inquiry.dto.response;

import com.team2final.minglecrm.inquiry.domain.ActionStatus;
import com.team2final.minglecrm.inquiry.domain.Inquiry;
import com.team2final.minglecrm.inquiry.domain.InquiryAction;
import com.team2final.minglecrm.inquiry.domain.InquiryReply;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class InquiryResponse {
    private Long id;
    private String customerName;
    private String customerPhone;
    private LocalDateTime date;
    private String type;
    private Boolean isReply;
    private String employName;
    private String inquiryTitle;
    private String inquiryContent;
    private ActionStatus actionStatus;

    @Builder
    public InquiryResponse(Long id, String customerName, String customerPhone, LocalDateTime date, String type, Boolean isReply, String employName, String inquiryTitle, String inquiryContent, ActionStatus actionStatus) {
        this.id = id;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.date = date;
        this.type = type;
        this.isReply = isReply;
        this.employName = employName;
        this.inquiryTitle = inquiryTitle;
        this.inquiryContent = inquiryContent;
        this.actionStatus = actionStatus;
    }

    public static InquiryResponse of(Inquiry inquiry, InquiryReply inquiryReply, InquiryAction inquiryAction) {
        String employName = (inquiryReply != null) ? inquiryReply.getEmployee().getName() : null;
        boolean isReply = (inquiryReply != null); // 답변이 있으면 true
        ActionStatus actionStatus = (inquiryAction != null) ? inquiryAction.getActionStatus() : ActionStatus.UNKNOWN;

        return InquiryResponse.builder()
                .id(inquiry.getId())
                .customerName(inquiry.getCustomer().getName())
                .customerPhone(inquiry.getCustomer().getPhone())
                .date(inquiry.getDate())
                .type(inquiry.getType())
                .employName(employName)
                .inquiryTitle(inquiry.getInquiryTitle())
                .inquiryContent(inquiry.getInquiryContent())
                .isReply(isReply)
                .actionStatus(actionStatus)
                .build();
    }

}

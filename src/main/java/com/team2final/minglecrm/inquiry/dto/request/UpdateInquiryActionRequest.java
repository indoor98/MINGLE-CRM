package com.team2final.minglecrm.inquiry.dto.request;

import com.team2final.minglecrm.inquiry.domain.ActionStatus;
import lombok.Getter;

@Getter
public class UpdateInquiryActionRequest {
    private String updateActionContent;
    private ActionStatus actionStatus;
}

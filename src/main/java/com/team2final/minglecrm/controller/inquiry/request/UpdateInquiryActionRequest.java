package com.team2final.minglecrm.controller.inquiry.request;

import com.team2final.minglecrm.entity.inquiry.ActionStatus;
import lombok.Getter;

@Getter
public class UpdateInquiryActionRequest {
    private String updateActionContent;
    private ActionStatus actionStatus;
}

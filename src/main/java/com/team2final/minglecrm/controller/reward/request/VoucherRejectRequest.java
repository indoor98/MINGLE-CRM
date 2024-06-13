package com.team2final.minglecrm.controller.reward.request;

import lombok.Getter;

@Getter
public class VoucherRejectRequest {
    private Long customerId;
    private String reason;
}

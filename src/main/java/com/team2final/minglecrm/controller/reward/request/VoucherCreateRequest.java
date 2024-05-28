package com.team2final.minglecrm.controller.reward.request;

import lombok.Getter;

@Getter
public class VoucherCreateRequest {
    private Long customerId;
    private Long amount;
}

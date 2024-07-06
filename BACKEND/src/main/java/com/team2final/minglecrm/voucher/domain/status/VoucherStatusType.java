package com.team2final.minglecrm.voucher.domain.status;

import lombok.Getter;

@Getter
public enum VoucherStatusType {
    REQUESTED,
    APPROVED,
    REJECTED,
    SENDED,
    CANCELED,
    CONVERTED
}

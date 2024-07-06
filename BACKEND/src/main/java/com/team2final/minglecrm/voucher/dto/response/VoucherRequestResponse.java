package com.team2final.minglecrm.voucher.dto.response;

import com.team2final.minglecrm.voucher.domain.VoucherHistory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class VoucherRequestResponse {
    private final Long customerId;
    private final Long employeeId;
    private final LocalDateTime requestDate;

    public static VoucherRequestResponse of(VoucherHistory voucherHistory) {
        return new VoucherRequestResponse(
                voucherHistory.getCustomer().getId(),
                voucherHistory.getEmployeeStaff().getId(),
                voucherHistory.getRequestedDate()
        );
    }

}
package com.team2final.minglecrm.controller.reward.response;

import com.team2final.minglecrm.entity.reward.VoucherHistory;
import com.team2final.minglecrm.entity.reward.status.VoucherStatusType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;


@Getter
@RequiredArgsConstructor
public class VoucherHistoryResponse {

    private final Long voucherHistoryId;
    private final Long voucherId;

    private final VoucherStatusType status;

    private final LocalDateTime requestDate;
    private final String createdReason;

    private final LocalDateTime confirmDate;
    private final LocalDateTime conversionDate;

    private final Long creatorId;
    private final String creatorName;

    private final Long approverId;
    private final String approverName;

    private final Long customerId;
    private final String customerName;

    private final Long amount;
    private final String rejectedReason;
    private final String voucherCode;


    public static VoucherHistoryResponse of(VoucherHistory voucherHistory){
        Long employeeStaffId = voucherHistory.getEmployeeStaff() != null ? voucherHistory.getEmployeeStaff().getId() : null;
        Long employeeManagerId = voucherHistory.getEmployeeManager() != null ? voucherHistory.getEmployeeManager().getId() : null;
        String employeeStaffName = voucherHistory.getEmployeeStaff() != null ? voucherHistory.getEmployeeStaff().getName() : null;
        String employeeManagerName = voucherHistory.getEmployeeManager() != null ? voucherHistory.getEmployeeManager().getName() : null;


        return new VoucherHistoryResponse(
                voucherHistory.getId(),
                voucherHistory.getVoucher().getId(),
                voucherHistory.getStatus(),
                voucherHistory.getRequestDate(),
                voucherHistory.getVoucher().getCreatedReason(),
                voucherHistory.getConfirmDate(),
                voucherHistory.getConversionDate(),

                employeeStaffId,
                employeeStaffName,

                employeeManagerId,
                employeeManagerName,

                voucherHistory.getCustomer().getId(),
                voucherHistory.getCustomer().getName(),

                voucherHistory.getVoucher().getAmount(),
                voucherHistory.getRejectedReason(),
                voucherHistory.getVoucherCode()
        );
    }

}
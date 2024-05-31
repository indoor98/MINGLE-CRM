package com.team2final.minglecrm.controller.reward.response;

import com.team2final.minglecrm.entity.customer.Customer;
import com.team2final.minglecrm.entity.employee.Employee;
import com.team2final.minglecrm.entity.reward.VoucherHistory;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@RequiredArgsConstructor
public class VoucherHistoryResponse {
    private final Long voucherId;
    private final LocalDateTime requestDate;
    private final Boolean isAuth;
    private final LocalDateTime authDate;
    private final Boolean isConvertedYn;
    private final LocalDateTime conversionDate;
    private final Long issuerId;
    private final Long approverId;
    private final Long customerId;


    public static VoucherHistoryResponse of(VoucherHistory voucherHistory){
        return new VoucherHistoryResponse(
                voucherHistory.getId(),
                voucherHistory.getRequestDate(),
                voucherHistory.getIsAuth(),
                voucherHistory.getAuthDate(),
                voucherHistory.getIsConvertedYn(),
                voucherHistory.getConversionDate(),
                voucherHistory.getEmployeeStaff().getId(),
                voucherHistory.getEmployeeManager().getId(),
                voucherHistory.getCustomer().getId()
        );
    }
}

package com.team2final.minglecrm.voucher.domain;

import com.team2final.minglecrm.customer.domain.Customer;
import com.team2final.minglecrm.employee.domain.Employee;
import com.team2final.minglecrm.voucher.domain.status.VoucherStatusType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VoucherHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable=false)
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;

    @Enumerated(value = EnumType.STRING)
    private VoucherStatusType status;
    private LocalDateTime requestDate; // 승인 요청 일시
    private LocalDateTime confirmDate; // 승인/거절 일시

    private LocalDateTime conversionDate; // 리워드 전환 일시

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "manager_id", nullable = true)
    private Employee employeeManager;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "staff_id")
    private Employee employeeStaff;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String voucherCode;
    private String rejectedReason;

    @Builder
    public VoucherHistory(Voucher voucher, LocalDateTime requestDate, VoucherStatusType status, LocalDateTime confirmDate, LocalDateTime conversionDate, Employee employeeManager, Employee employeeStaff, Customer customer, String voucherCode, String rejectedReason) {
        this.voucher = voucher;
        this.requestDate = requestDate;

        this.status = status;
        this.confirmDate = confirmDate;
        this.conversionDate = conversionDate;

        this.employeeManager = employeeManager;
        this.employeeStaff = employeeStaff;
        this.customer = customer;

        this.voucherCode = voucherCode;
        this.rejectedReason = rejectedReason;
    }

    public void approveVoucher(Employee approver, String generatedUniqueVoucherCode) {
        this.confirmDate = LocalDateTime.now();
        this.employeeManager = approver;
        this.status = VoucherStatusType.APPROVED;
        this.voucherCode = generatedUniqueVoucherCode;
    }

    public void rejectVoucher(String rejectedReason, Employee employeeManager) {
        this.status = VoucherStatusType.REJECTED;
        this.rejectedReason = rejectedReason;
        this.confirmDate = LocalDateTime.now();
        this.employeeManager = employeeManager;
    }

}
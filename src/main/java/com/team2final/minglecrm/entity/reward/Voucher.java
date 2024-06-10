package com.team2final.minglecrm.entity.reward;

import com.team2final.minglecrm.entity.customer.Customer;
import com.team2final.minglecrm.entity.employee.Employee;
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
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable=false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "issuer_id")
    private Employee employee;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private Long amount;
    private LocalDateTime createdDate;
    private LocalDateTime expiredDate;
    private String voucherCode;

    @Builder
    public Voucher(Employee employee, Customer customer, Long amount, LocalDateTime createdDate, LocalDateTime expiredDate, String voucherCode) {
        this.employee = employee;
        this.customer = customer;
        this.amount = amount;
        this.createdDate = createdDate;
        this.expiredDate = expiredDate;
        this.voucherCode = voucherCode;
    }
}

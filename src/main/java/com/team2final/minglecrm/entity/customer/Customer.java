package com.team2final.minglecrm.entity.customer;


import com.team2final.minglecrm.controller.customer.request.CustomerUpdateRequest;
import com.team2final.minglecrm.entity.employee.Employee;
import com.team2final.minglecrm.entity.reward.Reward;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    private String name;

    private String email;

    private String grade;

    private String phone;

    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private String memo;

    private String gender;

    private LocalDate birth;

    private LocalDate createdDate;

    @OneToOne(mappedBy = "customer")
    private Reward reward;

    private Boolean isDeleted;

    private Integer visitCnt;

    public void updateCustomerDetail(CustomerUpdateRequest customerUpdateRequest) {
        this.name = customerUpdateRequest.getName();
        this.gender = customerUpdateRequest.getGender();
        this.memo = customerUpdateRequest.getMemo();
        this.phone = customerUpdateRequest.getPhone();
        this.address = customerUpdateRequest.getAddress();
    }

    public void deleteCustomer() {
        this.isDeleted = true;
    }

    public void updateCustomerReservationDetail(String memo, String name) {
        this.memo = memo;
        this.name = name;
    }

    @Builder
    public Customer(Long id, String name, String grade, String email, String phone, String address, Employee employee, String memo, String gender, LocalDate birth, Reward reward, Boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.employee = employee;
        this.memo = memo;
        this.gender = gender;
        this.birth = birth;
        this.reward = reward;
        this.isDeleted = isDeleted;

    public void setEmail(String email) {
        this.email = email;
    }
}

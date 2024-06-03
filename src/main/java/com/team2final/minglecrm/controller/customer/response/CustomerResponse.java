package com.team2final.minglecrm.controller.customer.response;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CustomerResponse {

    private Long id;
    private String name;
    private String phone;
    private String employeeName;
    private String memo;
    private String grade;
    private String address;
    private String gender;
    private LocalDate birth;

    private Long amount;

    @Builder
    public CustomerResponse(Long id, String name, String grade, String phone, String address, String employeeName, String memo, String gender, LocalDate birth, Long amount) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.phone = phone;
        this.address = address;
        this.employeeName = employeeName;
        this.memo = memo;
        this.gender = gender;
        this.birth = birth;
        this.amount = amount;
    }
}

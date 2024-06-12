package com.team2final.minglecrm.controller.batch.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class BatchCustomerResponse {

    private Long id;
    private String name;
    private String phone;
    private String employeeName;
    private LocalDate createdDate;
    private String memo;
    private String grade;
    private String address;
    private String gender;
    private LocalDate birth;
    private Long amount;

    @Builder
    public BatchCustomerResponse(Long id, String name, String phone, String employeeName, LocalDate createdDate, String memo, String grade, String address, String gender, LocalDate birth, Long amount) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.employeeName = employeeName;
        this.createdDate = createdDate;
        this.memo = memo;
        this.grade = grade;
        this.address = address;
        this.gender = gender;
        this.birth = birth;
        this.amount = amount;
    }
}

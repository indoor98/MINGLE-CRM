package com.team2final.minglecrm.statistics.controller.customer.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class VisitCustomerResponse {

    private Long id;
    private String name;
    private String phone;
    private String employeeName;
    private LocalDate createdDate;
    private String grade;
    private String address;
    private String memo;
    private String gender;
    private LocalDate birth;
    private LocalDate visitStartDate;
    private LocalDate visitEndDate;

    @Builder
    public VisitCustomerResponse(Long id, String name, String phone, String employeeName, LocalDate createdDate, String grade, String address, String memo, String gender, LocalDate birth, LocalDate visitStartDate, LocalDate visitEndDate) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.employeeName = employeeName;
        this.createdDate = createdDate;
        this.grade = grade;
        this.address = address;
        this.memo = memo;
        this.gender = gender;
        this.birth = birth;
        this.visitStartDate = visitStartDate;
        this.visitEndDate = visitEndDate;
    }
}

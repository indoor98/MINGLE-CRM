package com.team2final.minglecrm.controller.statistics.customer.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class StatisticsCustomerResponse {

    private Long id;
    private String name;
    private String phone;
    private String employeeName;
    private LocalDate createdDate;
    private String memo;
    private String grade;
    private String address;
    private String gender;
    private Integer visitCnt;
    private LocalDate birth;
    private Long amount;

    @Builder
    public StatisticsCustomerResponse(Long id, String name, String phone, String employeeName, LocalDate createdDate, String memo, String grade, String address, String gender, Integer visitCnt, LocalDate birth, Long amount) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.employeeName = employeeName;
        this.createdDate = createdDate;
        this.memo = memo;
        this.grade = grade;
        this.address = address;
        this.gender = gender;
        this.visitCnt = visitCnt;
        this.birth = birth;
        this.amount = amount;
    }
}

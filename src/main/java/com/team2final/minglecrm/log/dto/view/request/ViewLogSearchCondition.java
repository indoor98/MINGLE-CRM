package com.team2final.minglecrm.log.dto.view.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ViewLogSearchCondition {

    private final String employeeName;
    private final String employeeGrade;
    private final String employeeEmail;
    private final String customerName;
    private final String customerGrade;
    private final String customerEmail;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public ViewLogSearchCondition(String employeeName, String employeeGrade, String employeeEmail, String customerName,
                                  String customerGrade, String customerEmail, LocalDateTime startDate, LocalDateTime endDate) {
        this.employeeName = employeeName;
        this.employeeGrade = employeeGrade;
        this.employeeEmail = employeeEmail;
        this.customerName = customerName;
        this.customerGrade = customerGrade;
        this.customerEmail = customerEmail;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}

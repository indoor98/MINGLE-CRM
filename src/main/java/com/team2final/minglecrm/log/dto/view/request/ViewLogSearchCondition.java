package com.team2final.minglecrm.log.dto.view.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ViewLogSearchCondition {

    private final String employeeName;
    private final String employeeGrade;
    private final String employeeEmail;
    private final String customerName;
    private final String customerGrade;
    private final String customerEmail;

    public ViewLogSearchCondition(String employeeName, String employeeGrade, String employeeEmail, String customerName, String customerGrade, String customerEmail) {
        this.employeeName = employeeName;
        this.employeeGrade = employeeGrade;
        this.employeeEmail = employeeEmail;
        this.customerName = customerName;
        this.customerGrade = customerGrade;
        this.customerEmail = customerEmail;
    }
}

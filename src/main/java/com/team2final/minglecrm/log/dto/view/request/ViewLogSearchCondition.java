package com.team2final.minglecrm.log.dto.view.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ViewLogSearchCondition {

    private final String employeeName;
    private final String employeeGrade;
    private final String employeeEmail;
    private final String customerName;
    private final String customerGrade;
    private final String customerEmail;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private final LocalDateTime startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private final LocalDateTime endDate;

    public static ViewLogSearchCondition of(String employeeName, String employeeGrade, String employeeEmail,
                                            String customerName, String customerGrade, String customerEmail,
                                            LocalDateTime startDate, LocalDateTime endDate) {
        return new ViewLogSearchCondition(
                employeeName, employeeGrade, employeeEmail, customerName, customerGrade, customerEmail, startDate, endDate
        );
    }
}

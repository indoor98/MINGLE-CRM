package com.team2final.minglecrm.employee.dto.response;

import com.team2final.minglecrm.employee.domain.Employee;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class EmployeeInfoResponse {

    private final String employeeName;
    private final String authority;
    private final String employeeEmail;
    private final LocalDateTime createdDate;

    public static EmployeeInfoResponse of(Employee employee) {
        return new EmployeeInfoResponse(
                employee.getName(),
                employee.getAuthority(),
                employee.getEmail(),
                employee.getCreatedDate()
        );
    }


}

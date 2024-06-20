package com.team2final.minglecrm.employee.dto.response;

import com.team2final.minglecrm.employee.domain.Employee;
import lombok.Getter;

@Getter
public class SignUpResponse {
    private final Long id;
    private final String email;
    private final String authority;

    private SignUpResponse(Long id, String email, String authority) {
        this.id = id;
        this.email = email;
        this.authority = authority;
    }
    public static SignUpResponse of(Employee employee) {
        return new SignUpResponse(
                employee.getId(),
                employee.getEmail(),
                employee.getAuthority());
    }

}

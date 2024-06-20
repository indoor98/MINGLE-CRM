package com.team2final.minglecrm.employee.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignInRequest {
    private String email;
    private String password;
}

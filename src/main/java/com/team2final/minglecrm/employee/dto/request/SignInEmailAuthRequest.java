package com.team2final.minglecrm.employee.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignInEmailAuthRequest {

    private final String email;
    private final String password;
}

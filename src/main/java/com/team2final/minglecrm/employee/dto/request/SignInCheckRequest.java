package com.team2final.minglecrm.employee.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignInCheckRequest {

    private final String email;
    private final String password;
    private final String authCode;
}

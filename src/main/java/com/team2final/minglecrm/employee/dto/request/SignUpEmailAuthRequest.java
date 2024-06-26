package com.team2final.minglecrm.employee.dto.request;

import lombok.Getter;


@Getter
public class SignUpEmailAuthRequest {

    private String authCode;
    private String email;
}

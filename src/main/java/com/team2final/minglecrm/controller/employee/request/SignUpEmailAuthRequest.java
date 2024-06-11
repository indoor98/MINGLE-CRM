package com.team2final.minglecrm.controller.employee.request;

import lombok.Getter;


@Getter
public class SignUpEmailAuthRequest {

    private String authCode;
    private String email;
}

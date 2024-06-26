package com.team2final.minglecrm.employee.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthEmailSendResponse {

    private final String status;
    private final Boolean data;
}

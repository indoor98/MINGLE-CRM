package com.team2final.minglecrm.registration.dto.request;

import com.team2final.minglecrm.employee.dto.request.SignUpRequest;
import lombok.Getter;

@Getter
public class RequestWrapper {

    private SignUpRequest signUpRequest;
    private RegistrationRequest registrationRequest;

}

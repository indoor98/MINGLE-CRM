package com.team2final.minglecrm.employee.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.team2final.minglecrm.common.exception.ResultResponse;
import com.team2final.minglecrm.employee.dto.request.*;
import com.team2final.minglecrm.employee.dto.response.*;
import com.team2final.minglecrm.service.email.EmailAuthService;
import com.team2final.minglecrm.auth.infrastructure.JwtProvider;
import com.team2final.minglecrm.employee.service.EmployeeService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmailAuthService emailAuthService;

    @PostMapping("/api/v1/auth/signup")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest requestDTO) {
        SignUpResponse responseDTO = employeeService.signUp(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/api/v1/auth/signup/emailauth")
    public ResponseEntity<AuthEmailSendResponse> AuthEmailSend(@RequestBody SignUpEmailRequest signUpEmailRequest) throws MessagingException {

        try {
            emailAuthService.SendSignUpAuthEmail(signUpEmailRequest.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK).body(new AuthEmailSendResponse("failed", false));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new AuthEmailSendResponse("secuccess", true));
    }

    @PostMapping("/api/v1/auth/authcheck")
    public ResponseEntity<AuthEmailCheckResponse> AuthEmailCheck(@RequestBody SignUpEmailAuthRequest request) {
        Boolean isCorrect = emailAuthService.AuthEmailCheck(request.getAuthCode(), request.getEmail());
        if (isCorrect) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new AuthEmailCheckResponse("success", true));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new AuthEmailCheckResponse("failed", false));
    }

}

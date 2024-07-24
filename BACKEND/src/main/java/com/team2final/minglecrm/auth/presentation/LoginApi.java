package com.team2final.minglecrm.auth.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.team2final.minglecrm.auth.dto.response.TokenResponse;
import com.team2final.minglecrm.auth.infrastructure.JwtUtil;
import com.team2final.minglecrm.common.exception.ResultResponse;
import com.team2final.minglecrm.common.infrastructure.CookieUtil;
import com.team2final.minglecrm.employee.dto.request.SignInRequest;
import com.team2final.minglecrm.employee.dto.response.AccessTokenResponse;
import com.team2final.minglecrm.employee.service.EmployeeService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LoginApi {

    private final JwtUtil jwtUtil;
    private final EmployeeService employeeService;
    private final CookieUtil cookieUtil;

    @PostMapping("/api/auth/login")
    public ResultResponse<AccessTokenResponse> login(@RequestBody SignInRequest request, HttpServletResponse response) throws JsonProcessingException {

        if(!employeeService.isValidEmailAndPassword(request)) {
            return new ResultResponse<>(HttpStatus.BAD_REQUEST.value(), "fail", null);
        }

        TokenResponse tokenResponse = jwtUtil.createTokensBySignIn(request.getEmail());
        response.addHeader(HttpHeaders.SET_COOKIE, cookieUtil.createRtkCookie(tokenResponse));
        AccessTokenResponse accessTokenResponse = AccessTokenResponse.builder()
                .atk(tokenResponse.getAtk())
                .atkExpiration(tokenResponse.getAtkExpiration())
                .build();

        return ResultResponse.success(HttpStatus.ACCEPTED.value(), "success", accessTokenResponse);
    }

    @GetMapping("/api/auth/reissue")
    public ResultResponse<AccessTokenResponse> reIssue(
            HttpServletResponse response,
            @CookieValue(value="rtk", defaultValue = "") String rtk
    ) throws JsonProcessingException {

        if (rtk.isEmpty()) {
            return new ResultResponse<>(HttpStatus.UNAUTHORIZED.value(), "fail", null);
        }

        TokenResponse tokenResponse = jwtUtil.renewToken(rtk);

        response.addHeader(HttpHeaders.SET_COOKIE, cookieUtil.createRtkCookie(tokenResponse));
        AccessTokenResponse accessTokenResponse = AccessTokenResponse.builder()
                .atk(tokenResponse.getAtk())
                .atkExpiration(tokenResponse.getAtkExpiration())
                .build();

        return ResultResponse.success(HttpStatus.ACCEPTED.value(), "success", accessTokenResponse);

    }

    @GetMapping("/api/auth/logout")
    public ResultResponse<Void> logout(HttpServletResponse response,
                                       @CookieValue(value="rtk", defaultValue = "") String rtk ) throws JsonProcessingException {

        if (rtk.isEmpty()) {
            return new ResultResponse<>(HttpStatus.BAD_REQUEST.value(), "fail", null);
        }

        response.addHeader(HttpHeaders.SET_COOKIE, cookieUtil.createDeleteCookie());

        employeeService.logout(rtk);
        return new ResultResponse<>(HttpStatus.OK.value(), "success", null);
    }

}

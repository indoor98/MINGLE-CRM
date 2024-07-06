package com.team2final.minglecrm.common.advice;


import com.team2final.minglecrm.common.exception.ResultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.BindException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler
    public ResultResponse<Void> handleException(BindException e) {
        return new ResultResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                null);
    }

    @ExceptionHandler
    public ResultResponse<Void> handleException(IllegalArgumentException e) {
        return new ResultResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
    }

    @ExceptionHandler
    public ResultResponse<Void> handleException(IllegalStateException e) {
        return new ResultResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
    }
}

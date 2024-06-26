//package com.team2final.minglecrm.common.handler;
//
//
//import com.team2final.minglecrm.common.exception.NotFoundException;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.crossstore.ChangeSetPersister;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.ErrorResponse;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
//
//@RestControllerAdvice
//@RequiredArgsConstructor
//public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
//
//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<ErrorResponse> handle(ChangeSetPersister.NotFoundException e, HttpServletRequest request) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.from(e));
//    }
//
//
//
//
//
//
//}

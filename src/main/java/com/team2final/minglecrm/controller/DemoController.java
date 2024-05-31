package com.team2final.minglecrm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 권한 관련 테스트 RestController입니다.
 * 지우셔도 무관합니다!
 */
@RestController
public class DemoController {

    @GetMapping("/authority/manager")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<String> authTestManager() {
        return new ResponseEntity<>("요청 승인", HttpStatus.OK);
    }

    @GetMapping("/authority/staff")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<String> authTestStaff() {
        return new ResponseEntity<>("요청 승인", HttpStatus.OK);
    }

}

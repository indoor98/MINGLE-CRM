package com.team2final.minglecrm.registration.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2final.minglecrm.employee.dto.request.SignUpRequest;
import com.team2final.minglecrm.registration.dto.request.RegistrationRequest;
import com.team2final.minglecrm.registration.dto.request.RequestWrapper;
import com.team2final.minglecrm.registration.dto.request.UpdateStatusRequest;
import com.team2final.minglecrm.registration.dto.response.RegistrationResponse;
import com.team2final.minglecrm.registration.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/registers")
public class RegistrationApi {

    private final RegistrationService registrationService;

    @GetMapping
    public ResponseEntity<Page<RegistrationResponse>> getRegistration(Pageable pageable) {
        Page<RegistrationResponse> registrationResponses = registrationService.getAllRegistration(pageable);
        return ResponseEntity.ok(registrationResponses);
    }



    @PostMapping("/updateStatus")
    public ResponseEntity<RegistrationResponse> updateEmployeeRequestStatus(
            @RequestParam String action,
            @RequestBody UpdateStatusRequest updateStatusRequest) {

        SignUpRequest signUpRequest = updateStatusRequest.getSignUpRequest();
        RegistrationRequest registrationRequest = updateStatusRequest.getRegistrationRequest();

        if ("approve".equalsIgnoreCase(action)) {
            registrationService.approvedEmployeeRequest(signUpRequest);
            return null;
        }

        if ("reject".equalsIgnoreCase(action)) {
            registrationService.rejectEmployeeRequest(signUpRequest);
            return null;
        }
        return null;
//        return ResponseEntity.badRequest().body(new RegistrationResponse("Invalid action"));
    }


}

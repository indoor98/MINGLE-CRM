package com.team2final.minglecrm.registration.service;

import com.team2final.minglecrm.employee.domain.repository.EmployeeRepository;
import com.team2final.minglecrm.employee.dto.request.SignUpRequest;
import com.team2final.minglecrm.employee.service.EmployeeService;
import com.team2final.minglecrm.registration.domain.Registration;
import com.team2final.minglecrm.registration.domain.repository.RegistrationRepository;
import com.team2final.minglecrm.registration.domain.type.RequestStatus;
import com.team2final.minglecrm.registration.dto.response.RegistrationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;

    private final PasswordEncoder passwordEncoder;

    public void sendRegistration(SignUpRequest requestDTO) {
        String encodedPassword = passwordEncoder.encode(requestDTO.getPassword());

        Registration registration = Registration
                .builder()
                .name(requestDTO.getName())
                .email(requestDTO.getEmail())
                .password(encodedPassword)
                .requestedRole(requestDTO.getAuthority())
                .status(RequestStatus.PENDING)
                .build();

        registrationRepository.save(registration);
    }

    public Page<RegistrationResponse> getPendingStatusEmployList(Pageable pageable) {
        pageable = (pageable == null) ? PageRequest.of(0, 5) : pageable;
        Page<Registration> registrations = registrationRepository.findByStatus(RequestStatus.PENDING, pageable);
        return registrations.map(RegistrationResponse::from);
    }

    public void approvedEmployeeRequest(SignUpRequest signUpRequest) {
        String email = signUpRequest.getEmail();
        Registration registration = registrationRepository.findByEmail(email);
        registration.changeStatus();
        employeeService.signUp(signUpRequest);
    }

    public void rejectEmployeeRequest(SignUpRequest signUpRequest) {

    }

    public Long getPendingCount() {
        return registrationRepository.countByStatus(RequestStatus.PENDING);
    }
}

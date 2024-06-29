package com.team2final.minglecrm.registration.service;

import com.team2final.minglecrm.employee.domain.repository.EmployeeRepository;
import com.team2final.minglecrm.employee.dto.request.SignUpRequest;
import com.team2final.minglecrm.employee.service.EmployeeService;
import com.team2final.minglecrm.registration.domain.Registration;
import com.team2final.minglecrm.registration.domain.repository.RegistrationRepository;
import com.team2final.minglecrm.registration.domain.repository.RegistrationSearchRepository;
import com.team2final.minglecrm.registration.domain.type.RequestStatus;
import com.team2final.minglecrm.registration.dto.request.RegistrationSearchCondition;
import com.team2final.minglecrm.registration.dto.response.RegistrationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final RegistrationSearchRepository registrationSearchRepository;
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
        Page<Registration> registrations = registrationRepository.findByStatusOrderByRegistrationRequestTimeDesc(RequestStatus.PENDING, pageable);
        return registrations.map(RegistrationResponse::from);
    }

    public Page<RegistrationResponse> getAllStatusEmployList(Pageable pageable) {
        pageable = (pageable == null) ? PageRequest.of(0, 5) : pageable;
        Page<Registration> registrations = registrationRepository.findAll(pageable);
        return registrations.map(RegistrationResponse::from);
    }



    @Transactional
    public void approvedEmployeeRequest(SignUpRequest signUpRequest) {
        String userEmail = getMangerEmail();
        Registration registration = getRequestedEmployeeEmail(signUpRequest);

        registration.approveChangeStatus(userEmail);
        employeeService.signUp(signUpRequest);
    }

    @Transactional
    public void rejectEmployeeRequest(SignUpRequest signUpRequest) {
        String userEmail = getMangerEmail();
        Registration registration = getRequestedEmployeeEmail(signUpRequest);

        registration.rejectedChangeStatus(userEmail);
    }

    private static String getMangerEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    private Registration getRequestedEmployeeEmail(SignUpRequest signUpRequest) {
        String email = signUpRequest.getEmail();
        return registrationRepository.findByEmail(email);
    }

    public Long getPendingCount() {
        return registrationRepository.countByStatus(RequestStatus.PENDING);
    }

    public Page<RegistrationResponse> searchRegistration(RegistrationSearchCondition condition, Pageable pageable) {
        return registrationSearchRepository.search(condition, pageable);
    }

}

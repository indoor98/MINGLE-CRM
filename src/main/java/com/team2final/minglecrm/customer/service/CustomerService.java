package com.team2final.minglecrm.customer.service;

//import com.team2final.minglecrm.common.exception.ErrorCode;
//import com.team2final.minglecrm.common.exception.NotFoundException;
import com.team2final.minglecrm.customer.domain.Customer;
import com.team2final.minglecrm.customer.domain.repository.CustomerRepository;
import com.team2final.minglecrm.customer.dto.request.CustomerUpdateRequest;
import com.team2final.minglecrm.customer.dto.response.CustomerDetailResponse;
import com.team2final.minglecrm.customer.dto.response.CustomerResponse;
import com.team2final.minglecrm.employee.domain.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

    //TODO : 권한
    // 고객 조회
    @Transactional(readOnly = true)
    public List<CustomerResponse> getAllCustomer(Pageable pageable) {
        pageable = pageable == null ? PageRequest.of(0, 3) : pageable;
        List<CustomerResponse> result = customerRepository.findAllBy(pageable).stream()
                .map(customer -> new CustomerResponse(
                        customer.getId(),
                        customer.getName(),
                        customer.getPhone(),
                        customer.getEmployee().getName(),
                        customer.getGrade(),
                        customer.getGender(),
                        customer.getBirth()
                ))
                .collect(Collectors.toList());

        return result;
    }

    public CustomerDetailResponse findById(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();

        if (customer.getIsDeleted()) {
            throw new RuntimeException("없슴둥");
        }

        return CustomerDetailResponse.of(customer);
    }

    public void updateCustomer(Long customerId, CustomerUpdateRequest customerUpdateRequest) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        customer.updateCustomerDetail(customerUpdateRequest);

    }

    public void deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        customer.deleteCustomer();
    }

}
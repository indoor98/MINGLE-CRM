package com.team2final.minglecrm.service.customer;

import com.team2final.minglecrm.controller.customer.request.CustomerUpdateRequest;
import com.team2final.minglecrm.controller.customer.response.CustomerResponse;
import com.team2final.minglecrm.entity.customer.Customer;
import com.team2final.minglecrm.persistence.repository.customer.CustomerRepository;
import com.team2final.minglecrm.persistence.repository.employee.EmployeeRepository;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

    // 고객 조회
    @Transactional(readOnly = true)
    public List<CustomerResponse> getAllCustomer(Pageable pageable) {
        pageable = pageable == null ? PageRequest.of(0, 3) : pageable;
        List<CustomerResponse> result = customerRepository.findAllBy(pageable).stream()
                .map(customer -> new CustomerResponse(
                        customer.getId(),
                        customer.getName(),
                        customer.getGrade(),
                        customer.getPhone(),
                        customer.getAddress(),
                        customer.getEmployee().getName(),
                        customer.getMemo(),
                        customer.getGender(),
                        customer.getBirth(),
                        customer.getReward().getAmount()
                ))
                .collect(Collectors.toList());

        return result;
    }

    public CustomerResponse findById(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();

        if (customer.getIsDeleted()) {
            throw new RuntimeException("없슴둥");
        }

        return CustomerResponse.builder()
                .id(customer.getId())
                .address(customer.getAddress())
                .amount(customer.getReward().getAmount())
                .grade(customer.getGrade())
                .memo(customer.getMemo())
                .birth(customer.getBirth())
                .employeeName(customer.getEmployee().getName())
                .phone(customer.getPhone())
                .name(customer.getName())
                .gender(customer.getGender())
                .build();
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
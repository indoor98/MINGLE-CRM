package com.team2final.minglecrm.controller.customer;

import com.team2final.minglecrm.controller.customer.request.CustomerUpdateRequest;
import com.team2final.minglecrm.controller.customer.response.CustomerDetailResponse;
import com.team2final.minglecrm.controller.customer.response.CustomerResponse;
import com.team2final.minglecrm.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerDetailApi {

    private final CustomerService customerService;

    // 고객 단건 조회
    @GetMapping("/{customerId}")
//    @PreAuthorize("hasAnyRole('STAFF', 'MANAGER')")
    public ResponseEntity<CustomerDetailResponse> customerDetail(@PathVariable Long customerId) {
        CustomerDetailResponse customerResponse = customerService.findById(customerId);
        return ResponseEntity.ok(customerResponse);
    }

    // 고객 정보 수정
    @PatchMapping("/{customerId}")
    @PreAuthorize("hasAnyRole('STAFF', 'MANAGER')")
    public void updateCustomer(@PathVariable Long customerId,
            @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        customerService.updateCustomer(customerId, customerUpdateRequest);
    }

    // 고객 삭제
    @DeleteMapping("/{customerId}")
    @PreAuthorize("hasAnyRole('STAFF', 'MANAGER')")
    public void deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
    }

}

package com.team2final.minglecrm.customer.presentation;

import com.team2final.minglecrm.customer.dto.response.CustomerResponse;
import com.team2final.minglecrm.customer.service.CustomerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerApi {
    private final CustomerService customerService;

    // TODO : paging 처리 -> 컨텐츠 총 개수 뽑기
    @GetMapping()
//    @PreAuthorize("hasAnyRole('STAFF', 'MANAGER')")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers(Pageable pageable) {
        List<CustomerResponse> customers = customerService.getAllCustomer(pageable);
        return ResponseEntity.ok(customers);
    }

}

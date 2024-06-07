package com.team2final.minglecrm.controller.customer.preference;

import com.team2final.minglecrm.controller.customer.preference.request.CustomerPreferenceUpdateRequest;
import com.team2final.minglecrm.controller.customer.preference.response.CustomerPreferenceResponse;
import com.team2final.minglecrm.entity.customer.CustomerPreference;
import com.team2final.minglecrm.service.customer.preference.CustomerPreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers/{customerId}/preferences")
public class CustomerPreferenceDetailApi {

    private final CustomerPreferenceService customerPreferenceService;

    // 개인 고객 별 선호도 조회
    @GetMapping
    public ResponseEntity<CustomerPreferenceResponse> getCustomerPreference(@PathVariable Long customerId) {
        CustomerPreferenceResponse customerPreference = customerPreferenceService.getCustomerPreference(customerId);
        return ResponseEntity.ok(customerPreference);
    }


    // 고객 -> 선호도 변경 API
    @PatchMapping()
    public void updateCustomerPreference(
            @PathVariable Long customerId,
            @RequestBody CustomerPreferenceUpdateRequest customerPreferenceUpdateRequest) {
        customerPreferenceService.updateCustomerPreference(customerId, customerPreferenceUpdateRequest);
    }


}

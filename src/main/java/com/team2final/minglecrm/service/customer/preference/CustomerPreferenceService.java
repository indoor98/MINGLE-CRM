package com.team2final.minglecrm.service.customer.preference;

import com.team2final.minglecrm.controller.customer.preference.request.CustomerPreferenceUpdateRequest;
import com.team2final.minglecrm.controller.customer.preference.response.CustomerPreferenceResponse;
import com.team2final.minglecrm.entity.customer.CustomerPreference;
import com.team2final.minglecrm.entity.customer.preference.CustomerPreferenceType;
import com.team2final.minglecrm.persistence.repository.customer.CustomerPreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerPreferenceService {

    private final CustomerPreferenceRepository customerPreferenceRepository;

    public CustomerPreferenceType[] getAllCustomerPreferences() {
        return CustomerPreferenceType.values();
    }


    public CustomerPreferenceResponse getCustomerPreference(Long customerId) {
        CustomerPreference customerPreference = customerPreferenceRepository.findByCustomerId(customerId);
        return CustomerPreferenceResponse.of(customerPreference);
    }


    @Transactional
    public void updateCustomerPreference(Long customerId, CustomerPreferenceUpdateRequest customerPreferenceUpdateRequest) {
        CustomerPreference customerPreference = customerPreferenceRepository.findByCustomerId(customerId);
        customerPreference.updateCustomerPreference(customerPreferenceUpdateRequest);
    }
}

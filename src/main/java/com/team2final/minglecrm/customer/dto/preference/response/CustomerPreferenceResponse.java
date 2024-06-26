package com.team2final.minglecrm.customer.dto.preference.response;

import com.team2final.minglecrm.customer.domain.CustomerPreference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Time;

@Getter
@RequiredArgsConstructor
public class CustomerPreferenceResponse {

    private final Boolean isSmoking;
    private final String purpose;
    private final Boolean isPet;
    private final Time preferredCheckinTime;
    private final Time preferredCheckoutTime;
    private final String dietaryRestrictions;
    private final String funnel;
    private final String interest;
    private final Boolean isBreakfastPreferred;

    public static CustomerPreferenceResponse of(CustomerPreference customerPreference) {
        return new CustomerPreferenceResponse(
                customerPreference.getIsSmoking(),
                customerPreference.getPurpose(),
                customerPreference.getIsPet(),
                customerPreference.getPreferredCheckinTime(),
                customerPreference.getPreferredCheckoutTime(),
                customerPreference.getDietaryRestrictions(),
                customerPreference.getFunnel(),
                customerPreference.getInterest(),
                customerPreference.getIsBreakfastPreferred()
        );
    }



}

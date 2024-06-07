package com.team2final.minglecrm.controller.customer.preference.request;

import lombok.Getter;

import java.sql.Time;
import java.time.LocalDateTime;

@Getter
public class CustomerPreferenceUpdateRequest {
    private Boolean isSmoking;
    private String purpose;
    private Boolean isPet;
    private Time preferredCheckinTime;
    private Time preferredCheckoutTime;
    private String dietaryRestrictions;
    private String funnel;
    private String interest;
    private Boolean isBreakfastPreferred;

}

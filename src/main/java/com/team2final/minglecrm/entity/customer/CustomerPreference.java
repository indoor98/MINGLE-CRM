package com.team2final.minglecrm.entity.customer;

import com.team2final.minglecrm.controller.customer.preference.request.CustomerPreferenceUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private Boolean isSmoking;
    private String purpose;
    private Boolean isPet;
    private Time preferredCheckinTime;
    private Time preferredCheckoutTime;
    private String dietaryRestrictions;
    private String funnel;
    private String interest;
    private Boolean isBreakfastPreferred;

    public void updateCustomerPreference(CustomerPreferenceUpdateRequest customerPreferenceUpdateRequest) {
        this.isSmoking = customerPreferenceUpdateRequest.getIsSmoking();
        this.purpose = customerPreferenceUpdateRequest.getPurpose();
        this.isPet = customerPreferenceUpdateRequest.getIsPet();
        this.preferredCheckinTime = customerPreferenceUpdateRequest.getPreferredCheckinTime();
        this.preferredCheckoutTime = customerPreferenceUpdateRequest.getPreferredCheckoutTime();
        this.dietaryRestrictions = customerPreferenceUpdateRequest.getDietaryRestrictions();
        this.funnel = customerPreferenceUpdateRequest.getFunnel();
        this.interest = customerPreferenceUpdateRequest.getInterest();
        this.isBreakfastPreferred = customerPreferenceUpdateRequest.getIsBreakfastPreferred();
    }
}


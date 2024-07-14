package com.team2final.minglecrm.review.dto.dining.request;

import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DiningReviewConditionSearchRequest {

    private String customerName;
    private String restaurant;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Builder
    public DiningReviewConditionSearchRequest(
            String customerName,
            String restaurant,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        this.customerName = customerName;
        this.restaurant = restaurant;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}

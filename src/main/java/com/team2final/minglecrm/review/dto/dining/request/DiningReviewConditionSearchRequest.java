package com.team2final.minglecrm.review.dto.dining.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class DiningReviewConditionSearchRequest {

    private String customerName;
    private String restaurant;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}

package com.team2final.minglecrm.review.dto.dining.request;

import com.team2final.minglecrm.review.domain.SummaryType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DiningReviewSummaryRequest {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String restaurant;
    private SummaryType summaryType;
}

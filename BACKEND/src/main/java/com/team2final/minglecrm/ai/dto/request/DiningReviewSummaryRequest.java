package com.team2final.minglecrm.ai.dto.request;

import com.team2final.minglecrm.review.domain.SummaryType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DiningReviewSummaryRequest {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String restaurant;
    private SummaryType summaryType;
}

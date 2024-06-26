package com.team2final.minglecrm.ai.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DiningReviewSummaryRequest {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String restaurant;
    private String summaryType;
}

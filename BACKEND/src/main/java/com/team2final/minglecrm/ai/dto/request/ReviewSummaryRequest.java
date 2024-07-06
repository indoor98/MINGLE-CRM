package com.team2final.minglecrm.ai.dto.request;

import com.team2final.minglecrm.review.domain.hotel.SummaryType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewSummaryRequest {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private SummaryType summaryType;
}

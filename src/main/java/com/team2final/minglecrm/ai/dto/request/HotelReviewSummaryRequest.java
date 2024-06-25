package com.team2final.minglecrm.ai.dto.request;

import com.team2final.minglecrm.review.domain.hotel.SummaryType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class HotelReviewSummaryRequest {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String hotel;
    private SummaryType summaryType;
}

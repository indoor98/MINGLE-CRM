package com.team2final.minglecrm.controller.ai.request;

import com.team2final.minglecrm.entity.hotel.type.SummaryType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewSummaryRequest {
    private LocalDateTime startDate;
    private SummaryType summaryType;
}

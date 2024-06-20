package com.team2final.minglecrm.ai.dto.response;

import com.team2final.minglecrm.review.domain.dining.DiningReviewSummary;
import com.team2final.minglecrm.review.domain.hotel.SummaryType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DiningReviewSummaryResponse {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private SummaryType summaryType;
    private String summary;

    @Builder
    DiningReviewSummaryResponse(LocalDateTime startDate, LocalDateTime endDate,  SummaryType summaryType, String summary) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.summaryType = summaryType;
        this.summary = summary;
    }

    static public DiningReviewSummaryResponse of(DiningReviewSummary diningReviewSummary) {
        return DiningReviewSummaryResponse
                .builder()
                .startDate(diningReviewSummary.getStartDate())
                .endDate(diningReviewSummary.getEndDate())
                .summaryType(diningReviewSummary.getSummaryType())
                .summary(diningReviewSummary.getSummary())
                .build();
    }
}

package com.team2final.minglecrm.ai.dto.response;

import com.team2final.minglecrm.review.domain.dining.DiningReviewSummary;
import com.team2final.minglecrm.review.domain.hotel.SummaryType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DiningReviewSummaryResponse {

    private LocalDateTime startDate;
    private SummaryType summaryType;
    private String summary;

    @Builder
    DiningReviewSummaryResponse(LocalDateTime startDate, SummaryType summaryType, String summary) {
        this.startDate = startDate;
        this.summaryType = summaryType;
        this.summary = summary;
    }

    static public DiningReviewSummaryResponse of(DiningReviewSummary diningReviewSummary) {
        return DiningReviewSummaryResponse
                .builder()
                .startDate(diningReviewSummary.getStartDate())
                .summaryType(diningReviewSummary.getSummaryType())
                .summary(diningReviewSummary.getSummary())
                .build();
    }
}

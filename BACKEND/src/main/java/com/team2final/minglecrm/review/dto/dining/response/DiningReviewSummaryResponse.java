package com.team2final.minglecrm.review.dto.dining.response;

import com.team2final.minglecrm.review.domain.dining.DiningReviewSummary;
import com.team2final.minglecrm.review.domain.SummaryType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DiningReviewSummaryResponse {

    private String summary;
    private Double averageRating;
    private Long reviewAmount;

    @Builder
    DiningReviewSummaryResponse(String summary, Double averageRating, Long reviewAmount) {
        this.summary = summary;
        this.averageRating = averageRating;
        this.reviewAmount = reviewAmount;
    }

}

package com.team2final.minglecrm.review.dto.hotel.response;

import com.querydsl.core.annotations.QueryProjection;
import com.team2final.minglecrm.review.domain.hotel.HotelReviewSummary;
import com.team2final.minglecrm.review.domain.hotel.SummaryType;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class HotelReviewSummaryResponse {

    private String summary;
    private Double averageRating;
    private Long reviewAmount;

    @Builder
    public HotelReviewSummaryResponse(String summary, Double averageRating, Long reviewAmount) {
        this.summary = summary;
        this.averageRating = averageRating;
        this.reviewAmount = reviewAmount;
    }

}

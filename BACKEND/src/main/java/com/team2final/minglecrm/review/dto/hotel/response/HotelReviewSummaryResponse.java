package com.team2final.minglecrm.review.dto.hotel.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

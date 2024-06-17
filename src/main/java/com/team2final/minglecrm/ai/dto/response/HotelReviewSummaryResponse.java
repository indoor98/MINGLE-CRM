package com.team2final.minglecrm.ai.dto.response;

import com.team2final.minglecrm.review.domain.hotel.HotelReviewSummary;
import com.team2final.minglecrm.review.domain.hotel.SummaryType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HotelReviewSummaryResponse {

    private LocalDateTime startDate;
    private SummaryType summaryType;
    private String summary;

    @Builder
    HotelReviewSummaryResponse(LocalDateTime startDate, SummaryType summaryType, String summary) {
        this.startDate = startDate;
        this.summaryType = summaryType;
        this.summary = summary;
    }

    static public HotelReviewSummaryResponse of(HotelReviewSummary hotelReviewSummary) {
        return HotelReviewSummaryResponse
                .builder()
                .startDate(hotelReviewSummary.getStartDate())
                .summaryType(hotelReviewSummary.getSummaryType())
                .summary(hotelReviewSummary.getSummary())
                .build();
    }

}

package com.team2final.minglecrm.ai.dto.response;

import com.team2final.minglecrm.review.domain.hotel.HotelReviewSummary;
import com.team2final.minglecrm.review.domain.hotel.SummaryType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HotelReviewSummaryResponse {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private SummaryType summaryType;
    private String summary;

    @Builder
    HotelReviewSummaryResponse(LocalDateTime startDate, LocalDateTime endDate, SummaryType summaryType, String summary) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.summaryType = summaryType;
        this.summary = summary;
    }

    static public HotelReviewSummaryResponse of(HotelReviewSummary hotelReviewSummary) {
        return HotelReviewSummaryResponse
                .builder()
                .startDate(hotelReviewSummary.getStartDate())
                .endDate(hotelReviewSummary.getEndDate())
                .summaryType(hotelReviewSummary.getSummaryType())
                .summary(hotelReviewSummary.getSummary())
                .build();
    }

}

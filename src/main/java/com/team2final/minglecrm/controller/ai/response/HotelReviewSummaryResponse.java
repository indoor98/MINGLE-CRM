package com.team2final.minglecrm.controller.ai.response;

import com.team2final.minglecrm.entity.hotel.HotelReviewSummary;
import com.team2final.minglecrm.entity.hotel.type.SummaryType;
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

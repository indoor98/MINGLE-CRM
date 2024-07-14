package com.team2final.minglecrm.review.dto.hotel.request;

import com.team2final.minglecrm.review.domain.hotel.Hotel;
import com.team2final.minglecrm.review.domain.SummaryType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class HotelReviewSummaryRequest {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Hotel hotel;
    private SummaryType summaryType;
}

package com.team2final.minglecrm.review.domain.hotel;

import com.querydsl.core.annotations.QueryProjection;
import com.team2final.minglecrm.review.dto.hotel.response.HotelReviewSummaryResponse;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class HotelReviewSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Hotel hotel;
    private SummaryType summaryType;
    private Double averageRating;
    private Long reviewAmount;

    @Column(length = 1000)
    private String summary;

    @Builder
    public HotelReviewSummary(
            LocalDateTime startDate,
            LocalDateTime endDate,
            Hotel hotel,
            String summary,
            SummaryType summaryType,
            Double averageRating,
            Long reviewAmount) {

        this.startDate = startDate;
        this.endDate = endDate;
        this.hotel = hotel;
        this.summary = summary;
        this.summaryType = summaryType;
        this.averageRating = averageRating;
        this.reviewAmount = reviewAmount;
    }

    public static HotelReviewSummaryResponse of(HotelReviewSummary summary) {
        return HotelReviewSummaryResponse.builder()
                .summary(summary.getSummary())
                .reviewAmount(summary.getReviewAmount())
                .averageRating(summary.getAverageRating())
                .build();
    }

}

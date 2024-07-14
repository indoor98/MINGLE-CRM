package com.team2final.minglecrm.review.domain.dining;

import com.team2final.minglecrm.review.dto.dining.response.DiningReviewSummaryResponse;
import com.team2final.minglecrm.review.domain.SummaryType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class DiningReviewSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private SummaryType summaryType;
    private String restaurant;
    private Double averageRating;
    private Long reviewAmount;

    @Column(length = 2000)
    private String summary;



    @Builder
    public DiningReviewSummary(
            LocalDateTime startDate,
            LocalDateTime endDate,
            String summary,
            SummaryType summaryType,
            String restaurant,
            Double averageRating,
            Long reviewAmount){

        this.startDate = startDate;
        this.endDate = endDate;
        this.summary = summary;
        this.summaryType = summaryType;
        this.restaurant = restaurant;
        this.averageRating = averageRating;
        this.reviewAmount = reviewAmount;
    }

    public static DiningReviewSummaryResponse of(DiningReviewSummary summary) {
        return DiningReviewSummaryResponse.builder()
                .summary(summary.getSummary())
                .reviewAmount(summary.getReviewAmount())
                .averageRating(summary.getAverageRating())
                .build();
    }
}

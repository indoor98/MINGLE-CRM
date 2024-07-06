package com.team2final.minglecrm.review.domain.dining;

import com.team2final.minglecrm.review.domain.hotel.SummaryType;
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

    @Column(length = 1000)
    private String summary;

    private String restaurant;

    @Builder
    public DiningReviewSummary(
            LocalDateTime startDate,
            LocalDateTime endDate,
            String summary,
            SummaryType summaryType,
            String restaurant) {

        this.startDate = startDate;
        this.endDate = endDate;
        this.summary = summary;
        this.summaryType = summaryType;
        this.restaurant = restaurant;
    }
}

package com.team2final.minglecrm.review.domain.hotel;

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

    private SummaryType summaryType;

    @Column(length = 1000)
    private String summary;

    @Builder
    public HotelReviewSummary(
            LocalDateTime startDate,
            String summary,
            SummaryType summaryType) {

        this.startDate = startDate;
        this.summary = summary;
        this.summaryType = summaryType;
    }


}

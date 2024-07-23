package com.team2final.minglecrm.review.dto.dining.response;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class DiningReviewConditionSearchResponse {

    private String customerName;
    private Double kindnessRating;
    private Double tasteRating;
    private Double cleanlinessRating;
    private Double atmosphereRating;
    private String review;
    private LocalDateTime createdTime;
    private String restaurant;

    @QueryProjection
    public DiningReviewConditionSearchResponse(
            String customerName,
            Double kindnessRating,
            Double tasteRating,
            Double cleanlinessRating,
            Double atmosphereRating,
            String review,
            LocalDateTime createdTime,
            String restaurant
    ) {
        this.customerName = customerName;
        this.kindnessRating = kindnessRating;
        this.cleanlinessRating = cleanlinessRating;
        this.tasteRating = tasteRating;
        this.atmosphereRating = atmosphereRating;
        this.review = review;
        this.createdTime = createdTime;
        this.restaurant = restaurant;
    }
}

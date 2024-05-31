package com.team2final.minglecrm.controller.dining.review.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DiningReviewResponse {

    private final Double tasteRating;
    private final Double kindnessRating;
    private final Double cleanlinessRating;
    private final Double atmosphereRating;
    private final String review;

}

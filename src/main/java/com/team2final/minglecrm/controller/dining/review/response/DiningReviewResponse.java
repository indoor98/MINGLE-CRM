package com.team2final.minglecrm.controller.dining.review.response;

import com.team2final.minglecrm.entity.dining.DiningReview;
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

    public static DiningReviewResponse of(DiningReview diningReview) {
        return new DiningReviewResponse(
                diningReview.getTasteRating(),
                diningReview.getKindnessRating(),
                diningReview.getCleanlinessRating(),
                diningReview.getAtmosphereRating(),
                diningReview.getReview());
    }

}

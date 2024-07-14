package com.team2final.minglecrm.review.dto.dining.response;

import com.team2final.minglecrm.reservation.dto.dining.response.DiningReviewConditionSearchResponse;

import java.util.List;

public record DiningReviewCombinedResponse(DiningReviewMetaDataResponse metaData,
                                           List<DiningReviewConditionSearchResponse> reviews) {
}

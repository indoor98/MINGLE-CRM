package com.team2final.minglecrm.review.dto.dining.response;

import java.util.List;

public record DiningReviewCombinedResponse(DiningReviewMetaDataResponse metaData,
                                           List<DiningReviewConditionSearchResponse> reviews) {
}

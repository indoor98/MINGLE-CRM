package com.team2final.minglecrm.review.dto.hotel.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

public record HotelReviewCombinedResponse(HotelReviewMetaDataResponse metaData,
                                          List<HotelReviewConditionSearchResponse> reviews) {

}

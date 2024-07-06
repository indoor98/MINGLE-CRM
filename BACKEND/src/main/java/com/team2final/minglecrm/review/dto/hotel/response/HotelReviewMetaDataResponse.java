package com.team2final.minglecrm.review.dto.hotel.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelReviewMetaDataResponse {

    private final Long RowsNumber;
    private final Long PagesNumber;

    @Builder
    public HotelReviewMetaDataResponse(Long rowsNumber, Long pagesNumber) {
        this.RowsNumber = rowsNumber;
        this.PagesNumber = pagesNumber;
    }
}

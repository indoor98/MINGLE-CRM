package com.team2final.minglecrm.review.dto.dining.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiningReviewMetaDataResponse {

    private final Long RowsNumber;
    private final Long PagesNumber;

    @Builder
    public DiningReviewMetaDataResponse(Long rowsNumber, Long pagesNumber) {
        this.RowsNumber = rowsNumber;
        this.PagesNumber = pagesNumber;
    }
}

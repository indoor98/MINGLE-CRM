package com.team2final.minglecrm.ai.dto.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelReviewForSummary {

    private String content;

    @QueryProjection
    public HotelReviewForSummary(String content) {
        this.content = content;
    }
}

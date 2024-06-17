package com.team2final.minglecrm.controller.ai.vo;

import com.querydsl.core.annotations.QueryProjection;
import com.team2final.minglecrm.entity.hotel.HotelReviewSummary;
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

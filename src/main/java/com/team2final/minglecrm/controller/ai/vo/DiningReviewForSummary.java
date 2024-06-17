package com.team2final.minglecrm.controller.ai.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiningReviewForSummary {

    private String content;

    @QueryProjection
    public DiningReviewForSummary(String content) {
        this.content = content;
    }
}

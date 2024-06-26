package com.team2final.minglecrm.statistics.dto.response.purchase;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StatisticsDishNameResponse {
    private String consumeType;
    private String dishName;
    private Long count;

    @QueryProjection
    public StatisticsDishNameResponse(String consumeType, String dishName, Long count) {
        this.consumeType = consumeType;
        this.dishName = dishName;
        this.count = count;
    }
}

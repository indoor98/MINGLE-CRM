package com.team2final.minglecrm.statistics.dto.response.purchase;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StatisticsGradeResponse {
    private String customerGrade;
    private String consumeType;
    private String dishName;
    private String roomType;
    private Long dishNameCount;
    private Long roomTypeCount;

    @QueryProjection
    public StatisticsGradeResponse(String customerGrade, String consumeType, String dishName, String roomType, Long dishNameCount, Long roomTypeCount) {
        this.customerGrade = customerGrade;
        this.consumeType = consumeType;
        this.dishName = dishName;
        this.roomType = roomType;
        this.dishNameCount = dishNameCount;
        this.roomTypeCount = roomTypeCount;
    }
}
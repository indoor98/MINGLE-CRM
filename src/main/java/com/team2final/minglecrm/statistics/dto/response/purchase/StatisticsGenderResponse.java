package com.team2final.minglecrm.statistics.dto.response.purchase;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StatisticsGenderResponse {
    private String customerGender;
    private String consumeType;
    private String dishName;
    private String roomType;
    private Long dishNameCount;
    private Long roomTypeCount;

    @QueryProjection
    public StatisticsGenderResponse(String customerGender, String consumeType, String dishName, String roomType, Long dishNameCount, Long roomTypeCount) {
        this.customerGender = customerGender;
        this.consumeType = consumeType;
        this.dishName = dishName;
        this.roomType = roomType;
        this.dishNameCount = dishNameCount;
        this.roomTypeCount = roomTypeCount;
    }
}

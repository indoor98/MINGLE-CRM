package com.team2final.minglecrm.statistics.dto.response.purchase;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class StatisticsRoomTypeResponse {
    private String consumeType;
    private String roomType;
    private Long count;

    @QueryProjection
    public StatisticsRoomTypeResponse(String consumeType, String roomType, Long count) {
        this.consumeType = consumeType;
        this.roomType = roomType;
        this.count = count;
    }
}


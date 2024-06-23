package com.team2final.minglecrm.statistics.dto.response.purchase;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class StatisticsResponse {

    private Long id;
    private LocalDate purchaseDate;
    private String customerName;
    private String customerGrade;
    private String customerGender;
    private String consumeType;
    private String dishName;
    private String roomType;
    private Long count;

    @Builder
    public StatisticsResponse(Long id, LocalDate purchaseDate, String customerName, String customerGrade, String customerGender, String consumeType, String dishName, String roomType, Long count) {
        this.id = id;
        this.purchaseDate = purchaseDate;
        this.customerName = customerName;
        this.customerGrade = customerGrade;
        this.customerGender = customerGender;
        this.consumeType = consumeType;
        this.dishName = dishName;
        this.roomType = roomType;
        this.count = count;
    }
}

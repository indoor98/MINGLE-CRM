package com.team2final.minglecrm.statistics.dto.response.dish;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class StatisticsDishResponse {

    private Long id;
    private LocalDate purchaseDate;
    private String name;
    private Integer quantity;
    private Long amount;

    @Builder
    public StatisticsDishResponse(Long id, LocalDate purchaseDate, String name, Integer quantity, Long amount) {
        this.id = id;
        this.purchaseDate = purchaseDate;
        this.name = name;
        this.quantity = quantity;
        this.amount = amount;
    }
}

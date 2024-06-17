package com.team2final.minglecrm.statistics.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class MonthlyReservationCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer reservationYear;
    private Integer reservationMonth;
    private Long reservationCount;

    @Builder
    public MonthlyReservationCount(Integer reservationYear, Integer reservationMonth, Long reservationCount) {
        this.reservationYear = reservationYear;
        this.reservationMonth = reservationMonth;
        this.reservationCount = reservationCount;
    }
}
